package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.Pet;
import pe.com.searchpet.models.Characteristic;
import pe.com.searchpet.models.requests.pets.CreateOnePet;
import pe.com.searchpet.models.requests.pets.PatchOnePet;
import pe.com.searchpet.models.requests.pets.PutOnePet;
import pe.com.searchpet.services.PetServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
@Validated
@AllArgsConstructor
public class PetController {

    private final PetServiceImpl petService;
    private final Logger LOG = LoggerFactory.getLogger(PetController.class);

    @PostMapping( value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pet> createOnePet(@ModelAttribute @Valid CreateOnePet pet, BindingResult resul){
        Pet newPet = petService.createOnePet(Pet
            .builder()
            .name(pet.getName())
            .idUser(new ObjectId(pet.getIdUser()))
            .idBreed(new ObjectId(pet.getIdBreed()))
            .description(pet.getDescription())
            .dateOfBirth(pet.getDateOfBirth())
            .characteristics(
                Characteristic
                    .builder()
                    .eyeColor(pet.getEyeColor())
                    .size(pet.getSize())
                    .hairColor(pet.getHairColor())
                    .build()
            )
            .build(),
            pet.getProfile()
        );
        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }

    @DeleteMapping("{idPet}")
    public ResponseEntity deleteOneById(@PathVariable(value = "idPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid String id){
        petService.deleteOnePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{idPet}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Pet findOneById(@PathVariable(value = "idPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid String id){
        return petService.findOneById(id);
    }

    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Pet> findAll(){
        return petService.all();
    }

    @PutMapping("{idPet}")
    public ResponseEntity<Pet> updateOnePet(@PathVariable(value = "idPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid  String id, @RequestBody @Valid PutOnePet pet){
        Pet updatedPet = petService.updateOne(
            Pet.builder()
                ._id(id)
                .name(pet.getName())
                .idUser(new ObjectId(pet.getIdUser()))
                .idBreed(new ObjectId(pet.getIdBreed()))
                .description(pet.getDescription())
                .dateOfBirth(pet.getDateOfBirth())
                .characteristics(
                    Characteristic
                        .builder()
                        .eyeColor(pet.getEyeColor())
                        .size(pet.getSize())
                        .hairColor(pet.getHairColor())
                        .build()
                )
                .build()
        );
        return new ResponseEntity<>(updatedPet, HttpStatus.OK);
    }

    @PatchMapping("{idPet}")
    public ResponseEntity<Pet> patchOnePet(@PathVariable(value = "idPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid String id, @RequestBody @Valid PatchOnePet pet){
        Pet updatedPet = petService.patchOne(
            Pet.builder()
                ._id(id)
                .name(pet.getName())
                .idUser(pet.getIdUser()!=null ? new ObjectId(pet.getIdUser()) : null)
                .idBreed(pet.getIdBreed()!=null ? new ObjectId(pet.getIdBreed()) : null)
                .description(pet.getDescription())
                .dateOfBirth(pet.getDateOfBirth())
                .characteristics(
                    pet.getEyeColor()==null &&
                    pet.getHairColor() == null &&
                    pet.getSize() == null ?
                        null
                        :Characteristic
                            .builder()
                            .eyeColor(pet.getEyeColor())
                            .size(pet.getSize())
                            .hairColor(pet.getHairColor())
                            .build()
                )
                .build()
        );
        return new ResponseEntity<>(updatedPet, HttpStatus.OK);
    }

}
