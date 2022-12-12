package pe.com.searchpet.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.searchpet.collections.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    @Query("{ '$or':[ {'username': ?0 }, { 'email':  ?1 }]}")
    Optional<User> findUserByEmailAndUsername(String username, String email);

    @Query("{ '$and': [ { '$or':[ {'username': ?0 }, { 'email':  ?1 } ] } , { _id: { '$ne': ?2 }  } ] }")
    Optional<User> findUserByEmailAndUsernameDistinctId(String username, String email, String idUser);

    @Query("{ 'email': ?0, _id: { '$ne': ?1} }")
    Optional<User> findUserByEmail(String email, String idUser);

    @Query("{ 'username': ?0, _id: { '$ne': ?1} }")
    Optional<User> findUserByUsername(String username, String idUser);

    @Query("{ 'status': 1}")
    List<User> findAll();

    @Query("{'_id': ?0, 'status': 1}")
    Optional<User> findById(String id);

}
