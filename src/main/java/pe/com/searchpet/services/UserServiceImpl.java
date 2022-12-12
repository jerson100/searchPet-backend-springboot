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
    public User patchOneUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.get_id());
        if(userOptional.isEmpty()){
            throw new BadRequestException("El usuario no existe");
        }
        User us = userOptional.get();
        if(user.getSocialNetWorks()!=null) us.setSocialNetWorks(user.getSocialNetWorks());
        if(user.getName()!=null) us.setName(user.getName());
        if(user.getAddress()!=null) us.setAddress(user.getAddress());
        if(user.getLocation()!=null) us.setLocation(user.getLocation());
        if(user.getEmail()!=null) {
            if(userRepository.findUserByEmail(user.getEmail(), user.get_id()).isPresent()){
                throw new BadRequestException("No se puede actualizar el usuario porque ya existe un usuario con el email indicado");
            }
            us.setEmail(user.getEmail());
        }
        if(user.getUsername()!=null) {
            if(userRepository.findUserByUsername(user.getUsername(), user.get_id()).isPresent()){
                throw new BadRequestException("No se puede actualizar el usuario porque ya existe un usuario con el username indicado");
            }
            us.setUsername(user.getUsername());
        }
        if(user.getBirthday()!=null) us.setBirthday(user.getBirthday());
        if(user.getMaternalSurname()!=null) us.setMaternalSurname(user.getMaternalSurname());
        if(user.getPaternalSurname()!=null) us.setPaternalSurname(user.getPaternalSurname());
        return userRepository.save(us);
    }

    @Override
    public User putOneUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.get_id());
        if(userOptional.isEmpty()){
            throw new BadRequestException("El usuario no existe");
        }
        if(userRepository.findUserByEmailAndUsernameDistinctId(user.getUsername(), user.getEmail(), user.get_id()).isPresent()){
            throw new BadRequestException("No se puede actualizar el usuario porque el nombre de usuario o email ya pertenecen a otro usuario");
        }
        User us = userOptional.get();
        us.setSocialNetWorks(user.getSocialNetWorks());
        us.setName(user.getName());
        us.setAddress(user.getAddress());
        us.setLocation(user.getLocation());
        us.setEmail(user.getEmail());
        us.setUsername(user.getUsername());
        us.setBirthday(user.getBirthday());
        us.setMaternalSurname(user.getMaternalSurname());
        us.setPaternalSurname(user.getPaternalSurname());
        return userRepository.save(us);
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
