package com.example.bootiful_restaurant_reviews.service;

import com.example.bootiful_restaurant_reviews.exception.RestaurantNotFoundException;
import com.example.bootiful_restaurant_reviews.model.Restaurant;
import com.example.bootiful_restaurant_reviews.repository.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Before
    public void setUp() {
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    public void getRestaurantsShouldReturnAllRestaurants() {
        given(restaurantRepository.findAll())
                .willReturn(Arrays.asList(new Restaurant(1L, "John Beef")));

        Iterable<Restaurant> restaurants = restaurantService.getRestaurants();
        assertThat(restaurants).isNotNull();
        assertThat(restaurants.iterator().hasNext()).isTrue();

        Restaurant restaurant = restaurants.iterator().next();
        assertThat(restaurant.getId()).isEqualTo(1L);
        assertThat(restaurant.getName()).isEqualTo("John Beef");
    }

    @Test
    public void getRestaurantByIdWhenExistsThenReturnsRestaurantDetails() {
        given(restaurantRepository.findById(1L))
                .willReturn(Optional.of(new Restaurant(1L, "John Beef")));

        Restaurant restaurant = restaurantService.getRestaurant(1L);

        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getId()).isEqualTo(1L);
        assertThat(restaurant.getName()).isEqualTo("John Beef");
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantByIdWhenNotExistsThenThrowsRestaurantNotFoundException() {
        restaurantService.getRestaurant(1L);
    }

    @Test
    public void createRestaurantThenReturnsPersistedRestaurant() {
        given(restaurantRepository.save(any()))
                .willReturn(new Restaurant(1L, "John Beef"));

        Restaurant persistedRestaurant = restaurantService
                .createRestaurant(new Restaurant(null, "John Beef"));

        assertThat(persistedRestaurant).isNotNull();
        assertThat(persistedRestaurant.getId()).isGreaterThan(0);
        assertThat(persistedRestaurant.getName()).isEqualTo("John Beef");
    }

    @Test
    public void updateRestaurantThenReturnsUpdatedRestaurant() {
        given(restaurantRepository.save(any()))
                .willReturn(new Restaurant(1L, "John Beef"));

        Restaurant updatedRestaurant = restaurantService
                .updateRestaurant(new Restaurant(1L, "John Beef"));

        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getId()).isEqualTo(1L);
        assertThat(updatedRestaurant.getName()).isEqualTo("John Beef");
    }

    @Test
    public void testDeleteRestaurantByIdGivenExistsByIdThenShouldCallDeleteById() {
        given(restaurantRepository.existsById(anyLong()))
                .willReturn(true);

        restaurantService.deleteRestaurantById(1L);

        verify(restaurantRepository, times(1)).existsById(1L);
        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteRestaurantByIdGivenNotExistsByIdThenShouldNotCallDeleteById() {
        given(restaurantRepository.existsById(anyLong()))
                .willReturn(false);

        restaurantService.deleteRestaurantById(1L);

        verify(restaurantRepository, times(1)).existsById(1L);
        verify(restaurantRepository, times(0)).deleteById(1L);
    }

}
