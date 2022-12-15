package pe.com.searchpet.services;

import org.springframework.web.multipart.MultipartFile;
import pe.com.searchpet.collections.Pet;

import java.util.List;

public interface IPetService {

    Pet createOnePet(Pet p, MultipartFile image_profile);
    void deleteOnePet(String id);
    List<Pet> all();
    Pet findOneById(String id);
    Pet updateOne(Pet p);
    Pet patchOne(Pet p);
}
