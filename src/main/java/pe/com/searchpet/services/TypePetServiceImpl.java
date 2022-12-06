package pe.com.searchpet.services;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.searchpet.collections.TypePet;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.repository.TypePetRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TypePetServiceImpl implements ITypePetService{
    private Logger LOG = LoggerFactory.getLogger(TypePetServiceImpl.class);
    @Autowired
    private TypePetRepository typePetRepository;

    @Override
    public TypePet findPetById(String id){
        Optional<TypePet> typePet = typePetRepository.findById(new ObjectId(id));
        if(typePet.isEmpty()){
            throw new ResourceNotFoundException("No se encontró la mascota con el id especificado");
        }
        return typePet.get();
    }

    @Override
    public List<TypePet> findAll(){
        return typePetRepository.findAll();
    }

    @Override
    public TypePet createPet(TypePet typePet) {
        Optional<TypePet> optionalTypePet = typePetRepository.findPetByType(typePet.getType());
        if(optionalTypePet.isPresent()) throw new BadRequestException("El tipo de mascota ya existe");
        Date date = new Date();
        typePet.setCreatedAt(date);
        typePet.setUpdatedAt(date);
        typePet.setStatus(1);
        LOG.info("Created TypePet:{}", typePet);
        return typePetRepository.save(typePet);
    }

    @Override
    public void deleteTypePetById(String id) {
        Optional<TypePet> typePetOptional = typePetRepository.findById(new ObjectId(id));
        if(typePetOptional.isEmpty()){
            throw new ResourceNotFoundException("El tipo de mascota con el id especificado no existe");
        }
        TypePet typePet = typePetOptional.get();
        typePet.setStatus(0);
        typePetRepository.save(typePet);
        LOG.info("Deleted TypePet:{}", typePetOptional.get());
    }

    @Override
    public TypePet updateOneTypePet(String id, TypePet t) {
        Optional<TypePet> prevTypePetOp = typePetRepository.findById(new ObjectId(id));
        if(prevTypePetOp.isEmpty()){
            throw new ResourceNotFoundException("El tipo de mascota no existe");
        }
        TypePet prevTypePet = prevTypePetOp.get();
        if(prevTypePet.equals(t)){
            throw new BadRequestException("El tipo de mascota especificado ya existe");
        }
        prevTypePet.setType(t.getType());
        prevTypePet.setDescription(t.getDescription());
        prevTypePet.setUpdatedAt(new Date());
        typePetRepository.save(prevTypePet);
        return prevTypePet;
    }

    @Override
    public TypePet patchOneTypePet(TypePet tp) {
        Optional<TypePet> prevTypePetOp = typePetRepository.findById(new ObjectId(tp.get_id()));
        if(prevTypePetOp.isEmpty()){
            throw new ResourceNotFoundException("El tipo de mascota que intenta actualizar no existe");
        }
        TypePet prevTypePet = prevTypePetOp.get();
        Optional<TypePet> existTypePetByName = typePetRepository
                .findPetByTypeDistincById(
                    tp.getType()!=null
                            ? tp.getType()
                            : prevTypePet.getType(),
                    new ObjectId(prevTypePet.get_id())
                );
        if(existTypePetByName.isPresent()){
            throw new BadRequestException("El tipo de mascota especificado ya existe, intente probar con otro tipo");
        }
        if(tp.getType() != null) prevTypePet.setType(tp.getType());
        if (tp.getDescription() != null) prevTypePet.setDescription(tp.getDescription());
        prevTypePet.setUpdatedAt(new Date());
        typePetRepository.save(prevTypePet);
        return prevTypePet;
    }
}
