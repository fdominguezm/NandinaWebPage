package com.nandina.api.services.interfaces;



import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Modifiers
    void createUser(User user);
    User save(User user);
    User saveProfilePicture(User user, byte[] image);
    User editUser(User user, String name, String password);

    // Getters
    boolean emailExists(String email);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    PagedContent<User> searchUsersByName(User user, String name, int page, int pageSize);

}


