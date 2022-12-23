package pe.com.searchpet.services;

import org.springframework.web.multipart.MultipartFile;
import pe.com.searchpet.collections.LostPet;

import java.util.List;

public interface ILostPetService {
    LostPet createOneLostPet(LostPet lostPet, List<MultipartFile> images);
    LostPet updateOneLostPet(LostPet lostPet);
    LostPet patchOneLostPet(LostPet lostPet);
    void deleteOneById(String id);
    List<LostPet> all();
    LostPet findOneById(String id);
}
