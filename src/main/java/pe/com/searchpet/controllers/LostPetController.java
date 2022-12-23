package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.LostPet;
import pe.com.searchpet.models.requests.lostPet.CreateOneLostPet;
import pe.com.searchpet.models.requests.lostPet.PatchOneLostPet;
import pe.com.searchpet.models.requests.lostPet.PutOneLostPet;
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
    private final Logger LOG = LoggerFactory.getLogger(LostPetController.class);

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

    @PatchMapping(value = "{idLostPet}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LostPet> patchOneLostPet(@PathVariable(value = "idLostPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del registro de la mascota perdida no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid  String id, @RequestBody @Valid PatchOneLostPet lostPet){
        LOG.warn(lostPet.toString());
        LostPet updatedLostPet = lostPetService.patchOneLostPet(LostPet
            .builder()
            ._id(id)
            .idPets(
                lostPet.getPets()!=null
                ? lostPet
                    .getPets()
                    .stream()
                    .map(idL -> new ObjectId(idL))
                    .collect(Collectors.toSet())
                : null
            )
            .location(
                lostPet.getLongitude() != 0 && lostPet.getLatitude() != 0
                ? new GeoJsonPoint(lostPet.getLongitude(),lostPet.getLatitude())
                : null
            )
            .description(lostPet.getDescription())
            .build()
        );
        return new ResponseEntity<>(updatedLostPet, HttpStatus.OK);
    }

    @PutMapping(value = "{idLostPet}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LostPet> putOneLostPet(@PathVariable(value = "idLostPet") @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del registro de la mascota perdida no tiene el formato correcto: ^[a-fA-F\\d]{24}$") @Valid  String id, @RequestBody @Valid PutOneLostPet lostPet){
        LostPet updatedLostPet = lostPetService.updateOneLostPet(LostPet
                .builder()
                ._id(id)
                .idPets(
                    lostPet
                        .getPets()
                        .stream()
                        .map(idL -> new ObjectId(idL))
                        .collect(Collectors.toSet())
                )
                .location(
                    lostPet.getLongitude() != 0 && lostPet.getLatitude() != 0
                    ? new GeoJsonPoint(lostPet.getLongitude(), lostPet.getLatitude())
                    : null
                )
                .description(lostPet.getDescription())
                .build()
        );
        return new ResponseEntity<>(updatedLostPet, HttpStatus.OK);
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
