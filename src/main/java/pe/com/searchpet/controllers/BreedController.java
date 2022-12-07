package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.Breed;
import pe.com.searchpet.collections.TypePet;
import pe.com.searchpet.models.CreateOneBreed;
import pe.com.searchpet.services.BreedServiceImpl;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/breeds" )
@AllArgsConstructor
public class BreedController {

    private BreedServiceImpl breedService;

    @GetMapping("{idBreed}/{typePet}")
    public ResponseEntity<Breed> test(@PathVariable(value = "idBreed") String idBreed, @PathVariable(value = "typePet") String typePet){
        Breed b = breedService.findByTypePetType(typePet);
        return ResponseEntity.ok(b);
    }

    @PostMapping(value = "",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateOneBreed> createBreed(@RequestBody @Valid CreateOneBreed breed){
        Breed newBreed = breedService.createOne(Breed
                .builder()
                .description(breed.getDescription())
                .name(breed.getName())
                .characteristics(breed.getCharacteristics())
                .typePet(TypePet
                        .builder()
                        ._id(breed.getTypePet())
                        .build())
                .build());
        breed.set_id(newBreed.get_id());
        breed.setCreatedAt(newBreed.getCreatedAt());
        breed.setUpdatedAt(newBreed.getUpdatedAt());
        return new ResponseEntity<>(breed, HttpStatus.CREATED);
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
