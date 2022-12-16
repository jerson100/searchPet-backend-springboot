package pe.com.searchpet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.com.searchpet.collections.LostPet;
import pe.com.searchpet.repository.LostpetRepository;

import java.util.List;

@Service
public class LostPetServiceImpl implements ILostPetService{

    @Autowired
    private LostpetRepository lostpetRepository;

    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudinary_cloud_name;
    @Value("${CLOUDINARY_API_KEY}")
    private String cloudinary_api_key;
    @Value("${CLOUDINARY_API_SRECET}")
    private String cloudinary_api_secret;

    @Override
    public LostPet createOneLostPet(LostPet lostPet) {
        return null;
    }

    @Override
    public LostPet updateOneLostPet(LostPet lostPet) {
        return null;
    }

    @Override
    public void deleteOneById(String id) {

    }

    @Override
    public List<LostPet> all() {
        return null;
    }

    @Override
    public LostPet findOneById(String id) {
        return null;
    }
}
