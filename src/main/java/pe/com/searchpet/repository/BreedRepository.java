package pe.com.searchpet.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.searchpet.collections.Breed;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreedRepository extends MongoRepository<Breed, ObjectId> {

    @Aggregation(pipeline = {
        "{ '$match': { '_id': ?0, 'status': 1 } }",
        "{ '$lookup': { 'from': 'typepets', 'localField':  'idTypePet', 'foreignField': '_id', as: 'typePet'}}",
        "{ '$unwind': { 'path': '$typePet','preserveNullAndEmptyArrays': false }}",
        "{ '$match': { 'typePet.status': 1 }}",
    })
    Optional<Breed> findById(String id);

    @Query("{ '?0': ?1, 'status': ?2}")
    Optional<Breed> findOneByKeyValue(String key, String value, int status);

    @Query("{ 'name': ?0, 'status': 1}")
    Optional<Breed> findExistBreed(String nameBreed);

    @Aggregation(pipeline = {
        "{ '$match': { 'status': 1 } }",
        "{ '$lookup': { 'from': 'typepets', 'localField':  'idTypePet', 'foreignField': '_id', as: 'typePet'}}",
        "{ '$unwind': { 'path': '$typePet','preserveNullAndEmptyArrays': false }}",
        "{ '$match': { 'typePet.status': 1 }}",
    })
    List<Breed> findAll();

}
