package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.TypePet;
import pe.com.searchpet.models.PatchTypePet;
import pe.com.searchpet.models.PutTypePet;
import pe.com.searchpet.services.TypePetServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/typepets")
@Validated
public class TypePetController {

    private Logger LOG = LoggerFactory.getLogger(TypePetController.class);

    @Autowired
    private TypePetServiceImpl typePetService;

    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TypePet> createType(@RequestBody @Valid TypePet typePetBody){
        return new ResponseEntity<>(typePetService.createPet(typePetBody), HttpStatus.CREATED);
    }

    @GetMapping("{idTypePet}")
    public ResponseEntity<TypePet> getTypePetById(@PathVariable("idTypePet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id especificado tiene el formato incorrecto") @Valid String id){
        TypePet typePet = typePetService.findPetById(id);
        return  ResponseEntity.ok(typePet);
    }

    @GetMapping(value="")
    public List<TypePet> getAllBreeds(){
        return typePetService.findAll();
    }

    @DeleteMapping(value="{idTypePet}")
    public ResponseEntity deleteTypePetById(@PathVariable(value = "idTypePet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id especificado tiene el formato incorrecto") @Valid String id) {
        typePetService.deleteTypePetById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value="{idTypePet}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TypePet> updateOneTypePet(@PathVariable(value = "idTypePet") @Pattern(regexp = "^[a-fA-F\\d]{24}$") @Valid String id, @RequestBody @Valid PutTypePet typePet){
        TypePet tp = TypePet.builder()
                .type(typePet.getType())
                ._id(id)
                .description(typePet.getDescription())
                .build();
        TypePet updatedTypePet = typePetService.updateOneTypePet(tp);
        return ResponseEntity.ok(updatedTypePet);
    }

    @PatchMapping(value="{idTypePet}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TypePet> patchOneTypePet(@PathVariable(value = "idTypePet") @Pattern(regexp = "^[a-fA-F\\d]{24}$") @Valid String id, @RequestBody @Valid PatchTypePet typePet){
        TypePet tp = TypePet.builder()
                .type(typePet.getType())
                .description(typePet.getDescription())
                ._id(id)
                .build();
        TypePet updatedTypePet = typePetService.patchOneTypePet(tp);
        return ResponseEntity.ok(updatedTypePet);
    }

}
