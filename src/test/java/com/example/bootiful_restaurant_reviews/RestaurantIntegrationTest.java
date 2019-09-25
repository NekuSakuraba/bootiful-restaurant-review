package com.example.bootiful_restaurant_reviews;

import com.example.bootiful_restaurant_reviews.model.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestEntityManager
public class RestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void getRestaurantsGivenItExitsThenReturnsRestaurantsDetails() throws Exception {
        Restaurant restaurant = testEntityManager.persistFlushFind(new Restaurant(null, "John Beef"));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("@.[0].id").value(restaurant.getId()))
                .andExpect(jsonPath("@.[0].name").value(restaurant.getName()));
    }

    @Test
    public void getRestaurantsByIdGivenItExitsThenReturnsRestaurantsDetails() throws Exception {
        Restaurant restaurant = testEntityManager.persistFlushFind(new Restaurant(null, "John Beef"));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/" + restaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("@.id").value(restaurant.getId()))
                .andExpect(jsonPath("@.name").value(restaurant.getName()));
    }

    @Test
    public void getRestaurantsByIdGivenItNotExitsThenReturnsRestaurantNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createRestaurantShouldReturnsStatusOk() throws Exception {
        String jsonContent = "{\"name\": \"John Beef\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
                .andExpect(status().isCreated());

        TypedQuery<Restaurant> query = testEntityManager.getEntityManager()
                .createQuery("from Restaurant r where r.name = 'John Beef'", Restaurant.class);

        Restaurant restaurant = query.getSingleResult();
        assertThat(restaurant.getId()).isNotNull();
        assertThat(restaurant.getId()).isGreaterThan(0L);
    }

    @Test
    public void deleteRestaurantGivenItExistsShouldDeleteRestaurantDetails() throws Exception {
        Restaurant restaurant = testEntityManager.persistFlushFind(new Restaurant(null, "John Beef"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/" + restaurant.getId()))
                .andExpect(status().isNoContent());

        restaurant = testEntityManager.find(Restaurant.class, restaurant.getId());
        assertThat(restaurant).isNull();
    }

    @Test
    public void updateRestaurantShouldUpdateRestaurantDetails() throws Exception {
        Restaurant restaurant = testEntityManager.persistFlushFind(new Restaurant(null, "John Beef"));
        String jsonContent = String.format("{\"id\": %d, \"name\": \"%s\"}", restaurant.getId(), "John Super Beef");

        mockMvc.perform(MockMvcRequestBuilders.put("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
                .andExpect(status().isNoContent());

        restaurant = testEntityManager.find(Restaurant.class, restaurant.getId());
        assertThat(restaurant.getName()).isEqualTo("John Super Beef");
    }

}
