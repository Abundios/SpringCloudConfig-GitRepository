package com.servicesinvestproducts.servicesinvestproducts.services;

import com.servicesinvestproducts.servicesinvestproducts.model.User;
import com.servicesinvestproducts.servicesinvestproducts.repos.UserRepository;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User getUserByUsername(String forename) {
        return userRepository.findByForename(forename);
    }

    public User getUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    public Integer create(final User user) {
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final User user) {
        final User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }
}