package pe.com.searchpet.services;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.com.searchpet.collections.Breed;
import pe.com.searchpet.collections.TypePet;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.repository.BreedRepository;
import pe.com.searchpet.repository.TypePetRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BreedServiceImpl implements IBreedService {

    private BreedRepository breedRepository;
    private TypePetRepository typePetRepository;
    //private MongoTemplate mongoTemplate;
    private final Logger LOG = LoggerFactory.getLogger(BreedServiceImpl.class);

    @Override
    public Breed findOneById(String id) {
        Optional<Breed> breed = breedRepository.findById(id);
        if(breed.isEmpty()) throw new ResourceNotFoundException("No se encontró la raza especificada");
        return breed.get();
    }

    @Override
    public List<Breed> all() {
        /*List<Breed> breeds = mongoTemplate.query(Breed.class)
                .matching(Query.query(where("typePet.status").gte(1))).all();*/
        return breedRepository.findAll();
    }

    @Override
    public void deleteOneById(String id) {
        Optional<Breed> breedOptional = breedRepository.findById(id);
        if(breedOptional.isEmpty()){
            throw new ResourceNotFoundException("No se pudo eliminar la raza especificada por que no existe");
        }
        Breed breed = breedOptional.get();
        breed.setStatus(0);
        breedRepository.save(breed);
    }

    @Override
    public Breed createOne(Breed b) {
        Optional<TypePet> typePetOptional = typePetRepository.findById(b.getIdTypePet());
        if (typePetOptional.isEmpty()) {
            throw new BadRequestException("No se pudo crear la raza porque el tipo de mascota no existe");
        }
        Optional<Breed> breedOptional = breedRepository.findExistBreed( b.getName());
        if (breedOptional.isPresent()) {
            throw new BadRequestException("No se pudo crear la raza porque el nombre de la raza ya existe");
        }
        b.setStatus(1);
        Breed newBreed = breedRepository.save(b);
        newBreed.setTypePet(typePetOptional.get());
        LOG.info("Created Breed: ",newBreed);
        return newBreed;
    }

    @Override
    public Breed updateOne(Breed b) {
        Optional<Breed> breedOptional = breedRepository.findById(b.get_id());
        if(breedOptional.isEmpty()) throw new ResourceNotFoundException("No se encontró la raza para el id especificado");
        Breed currentBreed = breedOptional.get();
        Optional<TypePet> typePetOptional = typePetRepository.findById(b.getIdTypePet());
        if (typePetOptional.isEmpty()) {
            throw new BadRequestException("No se pudo actualizar la raza porque el tipo de mascota no existe");
        }
        Optional<Breed> breedNameOptional = breedRepository.findExistBreed( b.getName());
        if (breedNameOptional.isPresent()) {
            throw new BadRequestException("No se pudo actualizar la raza porque ya existe uno con el mismo nombre");
        }
        currentBreed.setTypePet(typePetOptional.get());
        currentBreed.setIdTypePet(new ObjectId(typePetOptional.get().get_id()));
        currentBreed.setName(b.getName());
        currentBreed.setDescription(b.getDescription());
        currentBreed.setCharacteristics(b.getCharacteristics());
        LOG.info("Updated Breed: ",currentBreed);
        return breedRepository.save(currentBreed);
    }

    @Override
    public Breed updatePatchOne(Breed b) {
        Optional<Breed> breedOptional = breedRepository.findById(b.get_id());
        if(breedOptional.isEmpty()) throw new ResourceNotFoundException("No se encontró la raza para el id especificado");
        Breed breed = breedOptional.get();
        if(b.getIdTypePet() != null){
            Optional<TypePet> typePetOptional = typePetRepository.findById(b.getIdTypePet());
            if (typePetOptional.isEmpty()) {
                throw new BadRequestException("No se pudo actualizar la raza porque el tipo de mascota no existe");
            }
            TypePet typePet = typePetOptional.get();
            breed.setTypePet(typePet);
            breed.setIdTypePet(b.getIdTypePet());
        }
        if(b.getName()!=null){
            Optional<Breed> breedNameOptional = breedRepository.findExistBreed( b.getName());
            if (breedNameOptional.isPresent()) {
                throw new BadRequestException("No se pudo actualizar la raza porque ya existe uno con el mismo nombre");
            }
            breed.setName(b.getName());
        }
        if(b.getDescription()!=null) breed.setDescription(b.getDescription());
        if(b.getCharacteristics()!=null) breed.setCharacteristics(b.getCharacteristics());
        Breed patchBreed = breedRepository.save(breed);
        LOG.info("Patch Breed: ",patchBreed);
        return patchBreed;
    }



}
