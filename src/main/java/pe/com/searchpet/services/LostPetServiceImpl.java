package pe.com.searchpet.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.com.searchpet.collections.LostPet;
import pe.com.searchpet.collections.Pet;
import pe.com.searchpet.collections.User;
import pe.com.searchpet.enums.EStatus;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.repository.LostPetRepository;
import pe.com.searchpet.repository.PetRepository;
import pe.com.searchpet.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class LostPetServiceImpl implements ILostPetService{

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
            throw new BadRequestException("No se pudo crear la mascota perdida porque el usuario con el idEspecificado no existe");
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
                throw new BadRequestException("No se pudo crear la mascota perdida porque no se logró guardar la imagen, vuelva a intentarlo más tarde, gracias.");
            }
        }
        lostPet.setUser(userOptional.get());
        lostPet.setStatus(EStatus.ACTIVE.getStatus());
        lostPet.setImages(petImages);
        LostPet l = lostpetRepository.save(lostPet);
        return l;
    }

    @Override
    public LostPet updateOneLostPet(LostPet lostPet) {
        return null;
    }

    @Override
    public void deleteOneById(String id) {

    }

    @Override
    public List<LostPet> all() {
        return null;
    }

    @Override
    public LostPet findOneById(String id) {
        return null;
    }
}
