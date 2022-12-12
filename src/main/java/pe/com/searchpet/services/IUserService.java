package pe.com.searchpet.services;

import pe.com.searchpet.collections.User;

import java.util.List;

public interface IUserService {
    User addOneUser(User us);
    List<User> findAll();
    User patchOneUser(User us);
    User putOneUser(User us);
    void deleteOneUser(String id);
}
