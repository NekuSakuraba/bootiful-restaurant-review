package com.example.bootiful_restaurant_reviews.repository;

import com.example.bootiful_restaurant_reviews.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
