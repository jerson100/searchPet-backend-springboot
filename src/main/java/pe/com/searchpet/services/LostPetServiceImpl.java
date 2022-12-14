package pe.com.searchpet.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.com.searchpet.collections.LostPet;
import pe.com.searchpet.collections.Pet;
import pe.com.searchpet.collections.User;
import pe.com.searchpet.enums.EStatus;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.repository.LostPetRepository;
import pe.com.searchpet.repository.PetRepository;
import pe.com.searchpet.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LostPetServiceImpl implements ILostPetService{

    private final Logger LOG = LoggerFactory.getLogger(LostPetServiceImpl.class);
    @Autowired
    private LostPetRepository lostpetRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudinary_cloud_name;
    @Value("${CLOUDINARY_API_KEY}")
    private String cloudinary_api_key;
    @Value("${CLOUDINARY_API_SRECET}")
    private String cloudinary_api_secret;

    @Override
    public LostPet createOneLostPet(LostPet lostPet, List<MultipartFile> images) {
        Optional<User> userOptional = userRepository.findById(lostPet.getIdUser().toString());
        if(userOptional.isEmpty()){
            throw new BadRequestException("No se pudo crear la mascota perdida porque el usuario con el id especificado no existe");
        }
        Set<ObjectId> pets = lostPet.getIdPets();
        for(ObjectId id:pets){
            Optional<Pet> petOptional = petRepository.findById(id.toString());
            if(petOptional.isEmpty()){
                throw new BadRequestException("No se pudo crear la mascota perdida porque el id especificado para la mascota no existe");
            }
        }
        Set<String> petImages = new LinkedHashSet<>();
        for(MultipartFile m:images){
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudinary_cloud_name,
                    "api_key", cloudinary_api_key,
                    "api_secret", cloudinary_api_secret,
                    "secure", true));
            Map params = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true,
                    "resource_type", "auto",
                    "folder","sPetJava/pets/losts"
            );
            try {
                File f= Files.createTempFile("", m.getOriginalFilename()).toFile();
                m.transferTo(f);
                Map uploadResult  = cloudinary.uploader().upload(f, params);
                String secure_url = uploadResult.get("secure_url").toString();
                petImages.add(secure_url);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException("No se pudo crear la mascota perdida porque no se logr?? guardar la imagen, vuelva a intentarlo m??s tarde, gracias.");
            }
        }
        lostPet.setUser(userOptional.get());
        lostPet.setStatus(EStatus.ACTIVE.getStatus());
        lostPet.setImages(petImages);
        return lostpetRepository.save(lostPet);
    }

    @Override
    public LostPet updateOneLostPet(LostPet lostPet) {
        Optional<LostPet> lostPetOptional = lostpetRepository.findById(lostPet.get_id());
        if(lostPetOptional.isEmpty()){
            throw new ResourceNotFoundException("El registro de la mascota perdida con el id especificado no existe");
        };
        List<Pet> pets = petRepository.getPetsById(lostPet.getIdPets().stream().toList());
        if(lostPet.getIdPets().size()==0 || pets.size() != lostPet.getIdPets().size()){
            throw new BadRequestException("No se pudo actualizar el registro porque al menos una mascota con el id indicado no existe");
        }
        LostPet currentLostPet = lostPetOptional.get();
        currentLostPet.setIdPets(lostPet.getIdPets());
        currentLostPet.setPets(pets.stream().collect(Collectors.toSet()));
        currentLostPet.setDescription(lostPet.getDescription());
        currentLostPet.setLocation(lostPet.getLocation());
        lostpetRepository.save(currentLostPet);
        return currentLostPet;
    }

    @Override
    public LostPet patchOneLostPet(LostPet lostPet) {
        Optional<LostPet> lostPetOptional = lostpetRepository.findById(lostPet.get_id());
        if(lostPetOptional.isEmpty()){
            throw new ResourceNotFoundException("El registro de la mascota perdida con el id especificado no existe");
        };

        LostPet currentLostPet = lostPetOptional.get();
        if(lostPet.getIdPets()!=null){
            List<Pet> pets = petRepository.getPetsById(lostPet.getIdPets().stream().toList());
            if(lostPet.getIdPets().size()==0 || pets.size() != lostPet.getIdPets().size()){
                throw new BadRequestException("No se pudo actualizar el registro porque al menos una mascota con el id indicado no existe");
            }
            currentLostPet.setPets(pets.stream().collect(Collectors.toSet()));
            currentLostPet.setIdPets(lostPet.getIdPets());
        }

        LOG.info(lostPet.toString());
        if(lostPet.getDescription()!=null){
            currentLostPet.setDescription(lostPet.getDescription());
        }

        if(lostPet.getLocation()!=null){
            currentLostPet.setLocation(lostPet.getLocation());
        }

        return lostpetRepository.save(currentLostPet);
    }

    @Override
    public void deleteOneById(String id) {
        Optional<LostPet> lostPetOptional = lostpetRepository.findById(id);
        if(lostPetOptional.isEmpty() || lostPetOptional
                .get()
                .getPets()
                .size() == 0){
            throw new ResourceNotFoundException("No se pudo eliminar el registro de la mascota perdida porque no existe");
        }
        LostPet p = lostpetRepository.findById(new ObjectId(id)).get();
        p.setStatus(EStatus.INACTIVE.getStatus());
        lostpetRepository.save(p);
    }

    @Override
    public List<LostPet> all() {
        return lostpetRepository
                .findAll()
                .stream()
                .filter(lostPet -> lostPet.getPets().size() > 0)
                .toList();
    }

    @Override
    public LostPet findOneById(String id) {
        Optional<LostPet> lostPetOptional = lostpetRepository.findById(id);
        if(lostPetOptional.isEmpty() || lostPetOptional
                .get()
                .getPets()
                .size() == 0){
            throw new ResourceNotFoundException("El registro de la mascota perdida para el id especificado no existe");
        }
        return lostPetOptional.get();
    }

}
