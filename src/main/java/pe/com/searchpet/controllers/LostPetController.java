package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.searchpet.collections.LostPet;
import pe.com.searchpet.models.requests.lostPet.CreateOneLostPet;
import pe.com.searchpet.services.LostPetServiceImpl;

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
            .idPets(lostPet
                .getPets()
                .stream()
                .map(p -> new ObjectId(p.toString()))
                .collect(Collectors.toSet())
            )
            .idUser(new ObjectId(lostPet.getIdUser()))
            .location(new GeoJsonPoint(
                lostPet.getLatitude(),
                lostPet.getLongitude()
            ))
            .description(lostPet.getDescription())
            .build(),
            lostPet.getImages()
        );
        return new ResponseEntity<>(newLostPet, HttpStatus.CREATED);
    }


}
