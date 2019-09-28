package com.example.bootiful_restaurant_reviews.repository;

import com.example.bootiful_restaurant_reviews.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
