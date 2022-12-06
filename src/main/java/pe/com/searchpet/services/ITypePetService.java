package pe.com.searchpet.services;

import org.bson.types.ObjectId;
import pe.com.searchpet.collections.TypePet;

import java.util.List;

public interface ITypePetService {
    List<TypePet> findAll();
    TypePet createPet(TypePet t);
    TypePet findPetById(String id);
    void deleteTypePetById(String id);
    TypePet updateOneTypePet(String id, TypePet t);

    TypePet patchOneTypePet(TypePet tp);
}
