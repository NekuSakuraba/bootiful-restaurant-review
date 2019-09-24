package com.example.bootiful_restaurant_reviews.controller;

import com.example.bootiful_restaurant_reviews.exception.RestaurantNotFoundException;
import com.example.bootiful_restaurant_reviews.model.Restaurant;
import com.example.bootiful_restaurant_reviews.service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void getRestaurantsShouldReturnAllRestaurants() throws Exception {
        String uri = "/restaurants";

        given(restaurantService.getRestaurants())
                .willReturn(Arrays.asList(new Restaurant(1L, "John Beef")));

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("@.[0].id").value(1L))
                .andExpect(jsonPath("@.[0].name").value("John Beef"));
    }

    @Test
    public void getRestaurantByIdWhenExistsThenReturnsRestaurantDetails() throws Exception {
        String uri = "/restaurants/1";

        given(restaurantService.getRestaurant(1L))
                .willReturn(new Restaurant(1L, "John Beef"));

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("@.id").value(1L))
                .andExpect(jsonPath("@.name").value("John Beef"));
    }

    @Test
    public void getRestaurantByIdWhenNotExistsThenReturnsStatusNotFound() throws Exception {
        String uri = "/restaurants/1";

        given(restaurantService.getRestaurant(anyLong()))
                .willThrow(new RestaurantNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createRestaurantThenReturnsStatusCreated() throws Exception {
        String uri = "/restaurants";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(APPLICATION_JSON_UTF8).content("{}"))
                .andExpect(status().isCreated());

        verify(restaurantService, times(1)).createRestaurant(any());
    }

    @Test
    public void updateRestaurantThenReturnsStatusNoContent() throws Exception {
        String uri = "/restaurants";

        mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(APPLICATION_JSON_UTF8).content("{}"))
                .andExpect(status().isNoContent());

        verify(restaurantService, times(1)).updateRestaurant(any());
    }

    @Test
    public void deleteRestaurantThenReturnsStatusNoContent() throws Exception {
        String uri = "/restaurants/1";

        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isNoContent());

        verify(restaurantService, times(1)).deleteRestaurantById(1L);
    }

}
