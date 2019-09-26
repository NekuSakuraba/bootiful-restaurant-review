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
public class UserJpaTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testUserMapping() {
        User user = testEntityManager.persistFlushFind(
                new User(null, "John Doe", "MySuperSecret", "john.doe@email.com"));

        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getUsername()).isEqualTo("John Doe");
        assertThat(user.getPassword()).isEqualTo("MySuperSecret");
        assertThat(user.getEmail()).isEqualTo("john.doe@email.com");
    }

}
