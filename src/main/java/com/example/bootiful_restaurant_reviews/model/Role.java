package com.example.bootiful_restaurant_reviews.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<UserRole> users = new ArrayList<>();

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
