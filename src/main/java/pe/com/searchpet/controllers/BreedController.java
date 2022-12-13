package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.Breed;
import pe.com.searchpet.models.requests.breeds.CreateOneBreed;
import pe.com.searchpet.models.requests.breeds.PatchOneBreed;
import pe.com.searchpet.models.requests.breeds.UpdateOneBreed;
import pe.com.searchpet.services.BreedServiceImpl;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/breeds" )
@AllArgsConstructor
public class BreedController {

    private BreedServiceImpl breedService;

    @PostMapping(value = "",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Breed> createBreed(@RequestBody @Valid CreateOneBreed breed){
        Breed newBreed = breedService.createOne(Breed
                .builder()
                .description(breed.getDescription())
                .name(breed.getName())
                .characteristics(breed.getCharacteristics())
                .idTypePet(new ObjectId(breed.getIdTypePet()))
                .build());
        return new ResponseEntity<>(newBreed, HttpStatus.CREATED);
    }

    @PutMapping(value = "{idBreed}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Breed> updateBreed(@PathVariable("idBreed") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id de la raza no tiene el formato correcto") @Valid String idBreed, @RequestBody @Valid UpdateOneBreed breed){
        Breed updateBreed = breedService.updateOne(Breed
                .builder()
                .description(breed.getDescription())
                .name(breed.getName())
                .characteristics(breed.getCharacteristics())
                .idTypePet(breed.getIdTypePet()!=null?new ObjectId(breed.getIdTypePet()):null)
                ._id(idBreed)
                .build());
        return new ResponseEntity<>(updateBreed, HttpStatus.OK);
    }

    @PatchMapping(value = "{idBreed}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Breed> patchBreed(@PathVariable("idBreed") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id de la raza no tiene el formato correcto") @Valid String idBreed, @RequestBody @Valid PatchOneBreed breed){
        Breed patchBreed = breedService.updatePatchOne(Breed
                .builder()
                .description(breed.getDescription())
                .name(breed.getName())
                .characteristics(breed.getCharacteristics())
                .idTypePet(breed.getIdTypePet()!=null?new ObjectId(breed.getIdTypePet()):null)
                ._id(idBreed)
                .build());
        return new ResponseEntity<>(patchBreed, HttpStatus.OK);
    }

    @GetMapping(value = "{idBreed}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Breed> findBreedById(@PathVariable(value = "idBreed") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id especificado no tiene el formato correcto") @Valid String id){
        Breed breed = breedService.findOneById(id);
        return ResponseEntity.ok(breed);
    }

    @DeleteMapping(value = "{idBreed}")
    public ResponseEntity deleteOneBreed(@PathVariable(value = "idBreed") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id especificado no tiene el formato correcto") @Valid String id){
        breedService.deleteOneById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "")
    public List<Breed> getAllBreeds(){
        return breedService.all();
    }

}
