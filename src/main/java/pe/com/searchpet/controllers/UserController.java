package pe.com.searchpet.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.searchpet.collections.User;
import pe.com.searchpet.models.PatchOneUser;
import pe.com.searchpet.models.UpdateOneUser;
import pe.com.searchpet.services.UserServiceImpl;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.models.CreateOneUser;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> createOneUser(@RequestBody @Valid CreateOneUser user){
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new BadRequestException("Las contraseñas no coinciden");
        }
        User newUser = userService.addOneUser(User.
            builder()
            .name(user.getName())
            .paternalSurname(user.getPaternalSurname())
            .maternalSurname(user.getMaternalSurname())
            .birthday(user.getBirthday())
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .address(user.getAddress())
            .location(user.getLocation())
            .socialNetWorks(user.getSocialNetWorks())
            .build()
        );
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> allUsers(){
        return userService.findAll();
    }

    @DeleteMapping(value = "{idUser}")
    public ResponseEntity deleteOneUser(@PathVariable @Valid @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto") String idUser){
        userService.deleteOneUser(idUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{idUser}")
    public ResponseEntity<User> updateOneUser(@PathVariable @Valid @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto") String idUser, @RequestBody @Valid UpdateOneUser user){
        User updatedUser  = userService.putOneUser(
            User.builder()
            ._id(idUser)
            .socialNetWorks(user.getSocialNetWorks())
            .name(user.getName())
            .address(user.getAddress())
            .location(user.getLocation())
            .email(user.getEmail())
            .username(user.getUsername())
            .birthday(user.getBirthday())
            .maternalSurname(user.getMaternalSurname())
            .paternalSurname(user.getPaternalSurname())
            .build()
        );
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping(value = "{idUser}")
    public ResponseEntity<User> patchOneUser(@PathVariable @Valid @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto") String idUser, @RequestBody @Valid PatchOneUser user){
        User updatedUser  = userService.patchOneUser(
            User.builder()
            ._id(idUser)
            .socialNetWorks(user.getSocialNetWorks())
            .name(user.getName())
            .address(user.getAddress())
            .location(user.getLocation())
            .email(user.getEmail())
            .username(user.getUsername())
            .birthday(user.getBirthday())
            .maternalSurname(user.getMaternalSurname())
            .paternalSurname(user.getPaternalSurname())
            .build()
        );
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}

