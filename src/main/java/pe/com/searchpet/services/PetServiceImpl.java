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
import pe.com.searchpet.collections.Breed;
import pe.com.searchpet.collections.Pet;
import pe.com.searchpet.collections.User;
import pe.com.searchpet.enums.EStatus;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.repository.BreedRepository;
import pe.com.searchpet.repository.PetRepository;
import pe.com.searchpet.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PetServiceImpl implements IPetService{

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private BreedRepository breedRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudinary_cloud_name;
    @Value("${CLOUDINARY_API_KEY}")
    private String cloudinary_api_key;
    @Value("${CLOUDINARY_API_SRECET}")
    private String cloudinary_api_secret;

    private final Logger LOG = LoggerFactory.getLogger(PetServiceImpl.class);

    @Override
    public Pet createOnePet(Pet p, MultipartFile image_profile) {
        Optional<Breed> breedOptional = breedRepository.findById(p.getIdBreed().toString());
        if(breedOptional.isEmpty()) throw new BadRequestException("No se pudo crear la mascota porque la raza especificada no existe");
        Optional<User> userOptional =  userRepository.findById(p.getIdUser().toString());
        if(userOptional.isEmpty()) throw new BadRequestException("No se pudo crear la mascota porque el usuario especificado no existe");
        Optional<Pet> petOptional = petRepository.findByName(p.getName());
        if(petOptional.isPresent()) throw new BadRequestException("No se pudo crear la mascota porque el nombre indicado ya existe, porfavcr intente con otro nombre");
        if(!image_profile.isEmpty()){
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
                "folder","sPetJava/pets/profiles"
            );
            try {
                File f= Files.createTempFile("", image_profile.getOriginalFilename()).toFile();
                image_profile.transferTo(f);
                Map uploadResult  = cloudinary.uploader().upload(f, params);
                String secure_url = uploadResult.get("secure_url").toString();
                p.setUrlImageProfile(secure_url);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException("No se pudo crear la mascota porque no se logró guardar la imagen, vuelva a intentarlo más tarde, gracias.");
            }
        }
        p.setStatus(EStatus.ACTIVE.getStatus());
        Pet newP = petRepository.save(p);
        newP.setUser(userOptional.get());
        newP.setBreed(breedOptional.get());
        return newP;
    }

    @Override
    public void deleteOnePet(String id) {
        Optional<Pet> petOptional = petRepository.findById(id);
        if(petOptional.isEmpty()) throw new ResourceNotFoundException("No se pudo eliminar la mascota porque no existe");
        Pet p = petOptional.get();
        p.setStatus(EStatus.INACTIVE.getStatus());
        petRepository.save(p);
    }

    @Override
    public List<Pet> all(){
        return petRepository.findAll();
    }

    @Override
    public Pet findOneById(String id) {
        Optional<Pet> petOptional = petRepository.findById(id);
        if(petOptional.isEmpty()) throw new ResourceNotFoundException("La mascota con el id especificado no existe");
        return petOptional.get();
    }

    @Override
    public Pet updateOne(Pet p) {
        Optional<Pet> petOptional = petRepository.findById(p.get_id());
        if(petOptional.isEmpty()) throw new ResourceNotFoundException("La mascota para el id especificado no existe");
        Pet currentP =  petOptional.get();
        Optional<User> userOptional = userRepository.findById(p.getIdUser());
        if(userOptional.isEmpty()) throw new BadRequestException("No se pudo actualizar la mascota porque el usuario con el id especificado no existe");
        Optional<Breed> breedOptional = breedRepository.findById(p.getIdBreed());
        if(breedOptional.isEmpty()) throw new BadRequestException("No se pudo actualizar la mascota porque la raza especificada no existe");
        Optional<Pet> existP = petRepository.findByNameAndId(p.getName(), p.get_id());
        if(existP.isPresent()){
            throw new BadRequestException("No se pudo crear la mascota porque el nombre indicado ya existe");
        }
        currentP.setName(p.getName());
        currentP.setIdUser(p.getIdUser());
        currentP.setIdBreed(p.getIdBreed());
        currentP.setDescription(p.getDescription());
        currentP.setDateOfBirth(p.getDateOfBirth());
        currentP.setCharacteristics(p.getCharacteristics());
        return petRepository.save(currentP);
    }

    @Override
    public Pet patchOne(Pet p) {
        Optional<Pet> petOptional = petRepository.findById(p.get_id());
        if(petOptional.isEmpty()) throw new ResourceNotFoundException("La mascota para el id especificado no existe");
        Pet currentP =  petOptional.get();

        if(p.getIdUser()!=null){
            Optional<User> userOptional = userRepository.findById(p.getIdUser());
            if(userOptional.isEmpty()) throw new BadRequestException("No se pudo actualizar la mascota porque el usuario con el id especificado no existe");
            currentP.setIdUser(p.getIdUser());
        }

        if(p.getIdBreed()!=null){
            Optional<Breed> breedOptional = breedRepository.findById(p.getIdBreed());
            if(breedOptional.isEmpty()) throw new BadRequestException("No se pudo actualizar la mascota porque la raza especificada no existe");
            currentP.setIdBreed(p.getIdBreed());
        }

        if(p.getName()!=null){
            if(petRepository.findByNameAndId(p.getName(), p.get_id().toString()).isPresent()){
                throw new BadRequestException("No se pudo crear la mascota porque el nombre indicado ya existe");
            }
            currentP.setName(p.getName());
        }

        if(p.getDescription()!=null){
            currentP.setDescription(p.getDescription());
        }
        if(p.getDateOfBirth()!=null){
            currentP.setDateOfBirth(p.getDateOfBirth());
        }
        if(p.getCharacteristics()!=null){
            currentP.setCharacteristics(p.getCharacteristics());
        }
        return petRepository.save(currentP);
    }

}
