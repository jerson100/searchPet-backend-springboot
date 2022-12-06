package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.TypePet;
import pe.com.searchpet.services.TypePetService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/typepets")
@Validated
public class TypePetController {

    private Logger LOG = LoggerFactory.getLogger(TypePetController.class);

    @Autowired
    private TypePetService typePetService;

    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createType(@RequestBody @Valid TypePet typePetBody){
        LOG.info("created typepet");
        return new ResponseEntity<>(typePetService.createPet(typePetBody), HttpStatus.CREATED);
    };

    @GetMapping("{idTypePet}")
    public ResponseEntity<TypePet> getTypePetById(@PathVariable("idTypePet") ObjectId id){
        TypePet typePet = typePetService.findPetById(id);
        return  ResponseEntity.ok(typePet);
    }

    @GetMapping(value="")
    public List<TypePet> getAllBreeds(){
        return typePetService.findAll();
    }

}
