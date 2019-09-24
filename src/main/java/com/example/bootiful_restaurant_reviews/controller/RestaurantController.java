package com.example.bootiful_restaurant_reviews.controller;

import com.example.bootiful_restaurant_reviews.model.Restaurant;
import com.example.bootiful_restaurant_reviews.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public Iterable<Restaurant> getRestaurants() {
        return restaurantService.getRestaurants();
    }

    @GetMapping(value = "/restaurants/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Restaurant getRestaurant(@PathVariable Long id) {
        return restaurantService.getRestaurant(id);
    }

    @PostMapping("/restaurants")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRestaurant(@RequestBody Restaurant restaurant) {
        restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/restaurants")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant restaurant) {
        restaurantService.updateRestaurant(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurantById(id);
    }

}
