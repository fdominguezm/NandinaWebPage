package com.nandina.api.services.implementations;

import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.repositories.interfaces.UserRepository;
import com.nandina.api.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(User user) {
        user = userRepository.save(user);
    }


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveProfilePicture(User user, byte[] image) {
        user.setProfilePicture(image);
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user, String name, String password)  {
        user.setName(name);

        if(password != null){
            user.setPassword(password);
        }

        return userRepository.save(user);
    }


    @Override
    public boolean emailExists(String email) {
        return userRepository.emailExists(email);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public PagedContent<User> searchUsersByName(User user, String name, int page, int pageSize) {
        return userRepository.searchUsersByName(user, name, page, pageSize);
    }

    @Override
    public PagedContent<User> getFollowers(Long userId, int page, int pageSize) {
        return userRepository.getFollowers(userId, page, pageSize);
    }

    @Override
    public PagedContent<User> searchFollowers(Long userId, String name, int page, int pageSize) {
        return userRepository.searchFollowers(userId, name, page, pageSize);
    }

    @Override
    public PagedContent<User> getFollowing(Long userId, int page, int pageSize) {
        return userRepository.getFollowing(userId, page, pageSize);
    }

    @Override
    public PagedContent<User> searchFollowing(Long userId, String name, int page, int pageSize) {
        return userRepository.searchFollowing(userId, name, page, pageSize);
    }

    @Override
    public PagedContent<User> getFriends(Long userId, int page, int pageSize) {
        return userRepository.getFriends(userId, page, pageSize);
    }

}


