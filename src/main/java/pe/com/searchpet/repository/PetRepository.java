package pe.com.searchpet.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.searchpet.collections.Pet;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends MongoRepository<Pet, ObjectId> {
    @Query("{'name': ?0, 'status': 1}")
    Optional<Pet> findByName(String name);

    @Query("{'name': ?0, 'status': 1, '_id': { '$ne': ?1 } }")
    Optional<Pet> findByNameAndId(String name, String id);
    //@Query("{'_id': ?0, 'status': 1}")
    /*@Aggregation(pipeline = {
        "{'$match': { 'status': 1, _id: ?0 }}",
        "{'$lookup': { 'from': 'users', 'localField': 'idUser', 'foreignField': '_id', 'as': 'user'}}",
        "{'$unwind': { 'path': '$user', preserveNullAndEmptyArrays: false }}",
        "{'$match': { 'user.status': 1}}"
    })*/
    @Aggregation(pipeline = {
        "{'$match': { '_id': ?0, 'status': 1}}",
        "{'$lookup': { 'from': 'users', 'localField': 'idUser', 'foreignField': '_id', 'as': 'user'}}",
        "{'$unwind': { 'path': '$user', preserveNullAndEmptyArrays: false }}",
        "{'$match': { 'user.status': 1}}",
        "{'$lookup':" +
            "{" +
                "'from': 'breeds',"+
                "'let': {"+
                    "'idBreed': '$idBreed'"+
                "},"+
                "'pipeline': ["+
                    "{"+
                        "'$match': {"+
                            "'$expr':  {"+
                                "'$eq': ['$_id', '$$idBreed']"+
                            "},"+
                            "'status': 1"+
                        "}"+
                    "},"+
                    "{"+
                        "'$lookup':{"+
                            "'from': 'typepets',"+
                            "'let': {"+
                                "'idTypePet': '$idTypePet'"+
                            "},"+
                            "'pipeline': ["+
                                "{"+
                                    "'$match': {"+
                                        "'$expr': {"+
                                            "'$eq': ['$_id','$$idTypePet']"+
                                        "},"+
                                        "'status':1"+
                                    "}"+
                                "}"+
                            "],"+
                            "'as': 'typePet'"+
                        "}"+
                    "},"+
                    "{"+
                        "'$unwind': {"+
                            "'path': '$typePet',"+
                            "'preserveNullAndEmptyArrays': false"+
                        "}"+
                    "}"+
                "],"+
                "'as': 'breed'"+
            "}"+
        "}",
        "{" +
            "'$unwind': {"+
                "'path':'$breed'," +
                "'preserveNullAndEmptyArrays': false"+
            "}" +
        "}"
    })
    Optional<Pet> findById(String id);

    @Aggregation(pipeline = {
        "{'$match': { 'status': 1}}",
        "{'$lookup': { 'from': 'users', 'localField': 'idUser', 'foreignField': '_id', 'as': 'user'}}",
        "{'$unwind': { 'path': '$user', preserveNullAndEmptyArrays: false }}",
        "{'$match': { 'user.status': 1}}",
        "{'$lookup':" +
            "{" +
                "'from': 'breeds',"+
                "'let': {"+
                    "'idBreed': '$idBreed'"+
                "},"+
                "'pipeline': ["+
                    "{"+
                        "'$match': {"+
                            "'$expr':  {"+
                                "'$eq': ['$_id', '$$idBreed']"+
                            "},"+
                            "'status': 1"+
                        "}"+
                    "},"+
                    "{"+
                        "'$lookup':{"+
                            "'from': 'typepets',"+
                            "'let': {"+
                                "'idTypePet': '$idTypePet'"+
                            "},"+
                            "'pipeline': ["+
                                "{"+
                                    "'$match': {"+
                                        "'$expr': {"+
                                            "'$eq': ['$_id','$$idTypePet']"+
                                        "},"+
                                        "'status':1"+
                                    "}"+
                                "}"+
                            "],"+
                            "'as': 'typePet'"+
                        "}"+
                    "},"+
                    "{"+
                        "'$unwind': {"+
                            "'path': '$typePet',"+
                            "'preserveNullAndEmptyArrays': false"+
                        "}"+
                    "}"+
                "],"+
                "'as': 'breed'"+
            "}"+
        "}",
        "{" +
            "'$unwind': {"+
                "'path':'$breed'," +
                "'preserveNullAndEmptyArrays': false"+
            "}" +
        "}"
    })
    List<Pet> findAll();
}
