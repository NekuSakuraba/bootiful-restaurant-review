package com.example.bootiful_restaurant_reviews.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<UserRole> roles = new ArrayList<>();

    public User(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        role.getUsers().add(userRole);
        roles.add(userRole);
    }

    public void removeRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        role.getUsers().remove(userRole);
        roles.remove(userRole);

        userRole.setRole(null);
        userRole.setUser(null);
    }
}