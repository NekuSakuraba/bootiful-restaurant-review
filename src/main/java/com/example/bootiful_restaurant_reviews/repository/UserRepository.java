package com.example.bootiful_restaurant_reviews.repository;

import com.example.bootiful_restaurant_reviews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
