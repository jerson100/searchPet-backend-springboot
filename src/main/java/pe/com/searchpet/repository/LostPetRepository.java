package pe.com.searchpet.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.com.searchpet.collections.LostPet;

@Repository
public interface LostPetRepository extends MongoRepository<LostPet, ObjectId> {
}
