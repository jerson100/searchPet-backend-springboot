package pe.com.searchpet.services;

import pe.com.searchpet.collections.Breed;

import java.util.List;

public interface IBreedService {

    Breed findOneById(String id);

    List<Breed> all();

    void deleteOneById(String id);
    Breed createOne(Breed b);
    Breed updatePatchOne(Breed b);
    Breed updateOne(Breed b);

}
