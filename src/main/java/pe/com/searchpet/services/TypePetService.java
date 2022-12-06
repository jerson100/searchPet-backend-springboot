package pe.com.searchpet.services;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.searchpet.collections.TypePet;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.models.ApiError;
import pe.com.searchpet.repository.TypePetRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TypePetService {
    private Logger LOG = LoggerFactory.getLogger(TypePetService.class);
    @Autowired
    private TypePetRepository typePetRepository;

    public TypePet findPetById(ObjectId id){
        Optional<TypePet> typePet = typePetRepository.findById(id);
        if(!typePet.isPresent()){
            throw new ResourceNotFoundException("No se encontró la mascota con el id especificado");
        }
        return typePet.get();
    }

    public List<TypePet> findAll(){
        return typePetRepository.findAll();
    }

    public TypePet createPet(TypePet typePet) {
        Optional<TypePet> optionalTypePet = typePetRepository.findPetByType(typePet.getType());
        if(!optionalTypePet.isEmpty()) throw new BadRequestException("El tipo de mascota ya existe");
        Date date = new Date();
        typePet.setCreatedAt(date);
        typePet.setUpdatedAt(date);
        typePet.setStatus(1);
        LOG.info("Created TypePet:{}", typePet);
        return typePetRepository.save(typePet);
    }

}
