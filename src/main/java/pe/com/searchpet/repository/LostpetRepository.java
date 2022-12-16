package pe.com.searchpet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.com.searchpet.collections.LostPet;

public interface LostpetRepository extends MongoRepository<LostPet, String> {
}
