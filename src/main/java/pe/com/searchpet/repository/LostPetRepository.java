package pe.com.searchpet.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.com.searchpet.collections.LostPet;

import java.util.List;
import java.util.Optional;

@Repository
public interface LostPetRepository extends MongoRepository<LostPet, ObjectId> {

    @Aggregation(pipeline = {
        "{ " +
            "'$match': { " +
                "'status': 1," +
                "'_id': ?0" +
            "}" +
        "}",
        "{"+
            "'$lookup': {"+
                "'from': 'users',"+
                "'let': {"+
                    "'idUser': '$idUser'"+
                "},"+
                "'pipeline': ["+
                    "{"+
                        "'$match': {"+
                            "'status': 1,"+
                            "'$expr': {"+
                                "'$eq':  ['$_id', '$$idUser']"+
                            "}"+
                        "}"+
                    "}"+
                "],"+
                "'as': 'user',"+
            "}"+
        "}",
        "{"+
            "'$unwind': {"+
                "'path': '$user',"+
                "'preserveNullAndEmptyArrays': false"+
            "}"+
        "}",
        "{"+
            "'$lookup': {"+
                "'from': 'pets',"+
                "'let': {"+
                "'idPets': '$idPets',"+
                "},"+
                "'pipeline': ["+
                    "{"+
                        "'$match': {"+
                            "'status': 1,"+
                            "'$expr': {"+
                                "'$in': ['$_id', '$$idPets']"+
                            "}"+
                        "}"+
                    "}"+
                "],"+
                "'as':'pets'"+
            "}"+
        "}"
    })
    Optional<LostPet> findById(String idLostPet);

    @Aggregation(pipeline = {
        "{ '$match': { status: 1 }}",
        "{"+
            "'$lookup': {"+
                "'from': 'users',"+
                "'let': {"+
                    "'idUser': '$idUser'"+
                "},"+
                "'pipeline': ["+
                    "{"+
                        "'$match': {"+
                            "'status': 1,"+
                            "'$expr': {"+
                                "'$eq':  ['$_id', '$$idUser']"+
                            "}"+
                        "}"+
                    "}"+
                "],"+
                "'as': 'user',"+
            "}"+
        "}",
        "{"+
            "'$unwind': {"+
                "'path': '$user',"+
                "'preserveNullAndEmptyArrays': false"+
            "}"+
        "}",
        "{"+
            "'$lookup': {"+
                "'from': 'pets',"+
                "'let': {"+
                    "'idPets': '$idPets',"+
                "},"+
                "'pipeline': ["+
                    "{"+
                        "'$match': {"+
                            "'status': 1,"+
                            "'$expr': {"+
                                "'$in': ['$_id', '$$idPets']"+
                            "}"+
                        "}"+
                    "}"+
                "],"+
                "'as':'pets'"+
            "}"+
        "}"
    })
    List<LostPet> findAll();

}
