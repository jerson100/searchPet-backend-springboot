package pe.com.searchpet.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.searchpet.collections.User;
import pe.com.searchpet.enums.EAccountType;
import pe.com.searchpet.enums.EStatus;
import pe.com.searchpet.enums.ETypeUser;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @Override
    public User addOneUser(User us) {
        Optional<User> prevExistsUserOp = userRepository.findUserByEmailAndUsername(us.getUsername(), us.getEmail());

        if(prevExistsUserOp.isPresent()){
            throw new BadRequestException("El usuario ya existe, intente cambiar el email y username");
        }

        us.setStatus(EStatus.ACTIVE.getStatus());
        us.setTypeUser(ETypeUser.NORMAL.getTypeUser());
        us.setAccountType(EAccountType.NORMAL.getAccountType());
        us.setPassword(PASSWORD_ENCODER.encode(us.getPassword()));

        return userRepository.save(us);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User patchOneUser(User us) {
        return null;
    }

    @Override
    public User putOneUser(User us) {
        return null;
    }

    @Override
    public void deleteOneUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new ResourceNotFoundException("El usuario no existe");
        }
        User us = userOptional.get();
        us.setStatus(EStatus.INACTIVE.getStatus());
        userRepository.save(us);
    }

}
