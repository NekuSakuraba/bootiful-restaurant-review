package com.example.bootiful_restaurant_reviews.service;

import com.example.bootiful_restaurant_reviews.exception.UserAlreadyExistsException;
import com.example.bootiful_restaurant_reviews.model.Role;
import com.example.bootiful_restaurant_reviews.model.User;
import com.example.bootiful_restaurant_reviews.repository.RoleRepository;
import com.example.bootiful_restaurant_reviews.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User createUser(User user) {
        if (existsUserEmail(user)) {
            throw new UserAlreadyExistsException();
        }

        Role roleUser = roleRepository.findByName("ROLE_USER");
        user.addRole(roleUser);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean existsUserEmail(User user) {
        return findByEmail(user.getEmail()) != null;
    }

}
