package pe.com.searchpet.services;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private MongoTemplate mongoTemplate;
    private final Logger LOG = LoggerFactory.getLogger(BreedServiceImpl.class);

    @Override
    public Breed findOneById(String id) {
        Optional<Breed> breed = breedRepository.findOneByKeyValue("_id", id, 1);
        if(breed.isEmpty()) throw new ResourceNotFoundException("No se encontró la raza especificada");
        return breed.get();
    }

    @Override
    public List<Breed> all() {
        /*List<Breed> breeds = mongoTemplate.query(Breed.class)
                .matching(Query.query(where("typePet.status").gte(1))).all();*/
        return breedRepository
                .findAll()
                .stream()
                .filter(breed -> breed.getStatus() == 1)
                .toList();
    }

    @Override
    public void deleteOneById(String id) {
        Optional<Breed> breedOptional = breedRepository.findById(id);
        if(breedOptional.isEmpty() || breedOptional.get() == null || breedOptional.get().getStatus() == 0){
            throw new ResourceNotFoundException("No se pudo eliminar porque la raza no existe");
        }
        Breed breed = breedOptional.get();
        breed.setStatus(0);
        breedRepository.save(breed);
    }

    @Override
    public Breed createOne(Breed b) {
        Optional<TypePet> typePetOptional = typePetRepository.findById(new ObjectId(b.getTypePet().get_id()));
        if(typePetOptional.isEmpty()){
            throw new BadRequestException("No se pudo crear la raza porque el tipo de mascota no existe");
        }
        Optional<Breed> breedOptional = breedRepository.findOneByKeyValue("name", b.getName(), 1);
        if(breedOptional.isPresent()){
            throw new BadRequestException("No se pudo crear la raza porque el nombre de la raza ya existe");
        }
        b.setStatus(1);
        return breedRepository.save(b);
    }

    @Override
    public Breed findByTypePetType(String typePet) {
        Query query = new Query(Criteria
                .where("name").is("Desconocido")
                .and("status").is(1)
                .and("typePet.type").is(typePet)
        );
        Breed breedOptional = mongoTemplate.findOne(query, Breed.class, "breeds");
        LOG.info(String.valueOf(breedOptional));
        if(breedOptional==null) return null;
        return breedOptional;
    }


}
