package com.example.bootiful_restaurant_reviews.service;

import com.example.bootiful_restaurant_reviews.dto.UserDto;
import com.example.bootiful_restaurant_reviews.exception.UserAlreadyExistsException;
import com.example.bootiful_restaurant_reviews.model.User;
import com.example.bootiful_restaurant_reviews.repository.RoleRepository;
import com.example.bootiful_restaurant_reviews.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User createUser(UserDto userDto) {
        if (existsUserEmail(userDto)) {
            throw new UserAlreadyExistsException();
        }

        User user = new User()
                .addRole(roleRepository.findByName("ROLE_USER"));
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return userRepository.save(user);
    }

    private boolean existsUserEmail(UserDto user) {
        return userRepository.existsByEmail(user.getEmail());
    }

}
