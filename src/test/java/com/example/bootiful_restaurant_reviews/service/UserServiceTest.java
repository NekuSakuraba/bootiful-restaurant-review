package com.example.bootiful_restaurant_reviews.service;

import com.example.bootiful_restaurant_reviews.dto.UserDto;
import com.example.bootiful_restaurant_reviews.exception.UserAlreadyExistsException;
import com.example.bootiful_restaurant_reviews.model.Role;
import com.example.bootiful_restaurant_reviews.model.User;
import com.example.bootiful_restaurant_reviews.repository.RoleRepository;
import com.example.bootiful_restaurant_reviews.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        userService = new UserService(userRepository, roleRepository);
    }

    @Test
    public void createUserShouldReturnPersistedUser() {
        given(roleRepository.findByName(anyString()))
                .willReturn(new Role(1L, "ROLE_USER"));

        given(userRepository.save(any(User.class)))
                .willReturn(
                        new User(1L, "John Doe", "MySecret", "john.doe@example.com")
                                .addRole(new Role(1L, "ROLE_USER")));

        User user = userService
                .createUser(new UserDto("John Doe", "MySecret", "john.doe@example.com"));

        assertThat(user.getId()).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("John Doe");

        verify(roleRepository, times(1)).findByName(anyString());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void whenCreateUserGivenEmailExistsThenThrowsUserAlreadyExistsException() {
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        userService.createUser(new UserDto("John Doe", "MySecret", "john.doe@example.com"));
    }

}
