package pe.com.searchpet.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pe.com.searchpet.collections.TypePet;

import java.util.List;
import java.util.Optional;

public interface TypePetRepository extends MongoRepository<TypePet, ObjectId> {

    @Query("{'status': 1, '_id':  ?0}")
    Optional<TypePet> findById(ObjectId id);

    @Query("{'status': 1}")
    List<TypePet> findAll();

    @Query("{'status': 1, 'type': ?0}")
    Optional<TypePet> findPetByType(String name);

    @Query("{'status':1, 'type': ?0, '_id': {'$ne':?1} }")
    Optional<TypePet> findPetByTypeDistincById(String name, ObjectId id);

}
