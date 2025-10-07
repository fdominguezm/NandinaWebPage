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
    PagedContent<User> getFollowers(Long userId, int page, int pageSize);
    PagedContent<User> searchFollowers(Long userId, String name, int page, int pageSize);
    PagedContent<User> getFollowing(Long userId, int page, int pageSize);
    PagedContent<User> searchFollowing(Long userId, String name, int page, int pageSize);
    PagedContent<User> getFriends(Long userId, int page, int pageSize);
    PagedContent<User> searchFriends(Long userId, String name, int page, int pageSize);

}

