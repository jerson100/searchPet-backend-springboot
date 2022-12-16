package pe.com.searchpet.services;

import pe.com.searchpet.collections.LostPet;

import java.util.List;

public interface ILostPetService {
    LostPet createOneLostPet(LostPet lostPet);
    LostPet updateOneLostPet(LostPet lostPet);
    void deleteOneById(String id);
    List<LostPet> all();
    LostPet findOneById(String id);
}
