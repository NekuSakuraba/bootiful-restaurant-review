package com.example.bootiful_restaurant_reviews.service;

import com.example.bootiful_restaurant_reviews.exception.RestaurantNotFoundException;
import com.example.bootiful_restaurant_reviews.model.Restaurant;
import com.example.bootiful_restaurant_reviews.repository.RestaurantRepository;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Iterable<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurantById(Long id) {
        restaurantRepository.deleteById(id);
    }
}
