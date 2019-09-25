package com.example.bootiful_restaurant_reviews.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class RestaurantJpaTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testRestaurantMapping() {
        Restaurant restaurant = testEntityManager.persistFlushFind(new Restaurant(null, "John Beef"));

        assertThat(restaurant.getId()).isGreaterThan(0L);
        assertThat(restaurant.getName()).isEqualTo("John Beef");

        Long id = restaurant.getId();

        restaurant.setName("John Super");
        restaurant = testEntityManager.persistFlushFind(restaurant);

        assertThat(restaurant.getId()).isEqualTo(id);
        assertThat(restaurant.getName()).isEqualTo("John Super");
    }
}
