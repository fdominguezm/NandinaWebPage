package com.nandina.api.repositories.interfaces;

import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long userId);
    PagedContent<User> searchUsersByName(User user, String name, int page, int pageSize);
    boolean emailExists(String email);

}

