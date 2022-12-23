package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.LostPet;
import pe.com.searchpet.models.requests.lostPet.CreateOneLostPet;
import pe.com.searchpet.services.LostPetServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/lostpets")
public class LostPetController {

    private LostPetServiceImpl lostPetService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LostPet> createOneLostPet(@Valid @ModelAttribute CreateOneLostPet lostPet, BindingResult result){
        LostPet newLostPet = lostPetService.createOneLostPet(LostPet
            .builder()
            .idPets(
                Arrays.stream(lostPet.getPets().split(",")).map(ObjectId::new).collect(Collectors.toSet())
            )
            .idUser(new ObjectId(lostPet.getIdUser()))
            .location(new GeoJsonPoint(
                lostPet.getLongitude(),
                lostPet.getLatitude()
            ))
            .description(lostPet.getDescription())
            .build(),
            lostPet.getImages()
        );
        return new ResponseEntity<>(newLostPet, HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<LostPet> getAllLostPets(){
        return lostPetService.all();
    }

    @GetMapping(value = "{idLostPet}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public LostPet getLostPetById(@PathVariable(value = "idLostPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del registro de la mascota perdida no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid  String id){
        return lostPetService.findOneById(id);
    }

    @DeleteMapping(value = "{idLostPet}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneLostPet(@PathVariable(value = "idLostPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del registro de la mascota perdida no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid  String id){
        lostPetService.deleteOneById(id);
    }


}
