package com.example.bootiful_restaurant_reviews.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    @NaturalId
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<UserRole> roles = new ArrayList<>();

    public User(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        role.getUsers().add(userRole);
        roles.add(userRole);
        return this;
    }

    public User removeRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        role.getUsers().remove(userRole);
        roles.remove(userRole);

        userRole.setRole(null);
        userRole.setUser(null);
        return this;
    }
}
