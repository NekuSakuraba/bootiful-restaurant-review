package com.example.bootiful_restaurant_reviews.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserJpaTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testUserMapping() {
        User user = new User(null, "John Doe", "MySuperSecret", "john.doe@email.com");

        capture.reset();
        testEntityManager.persistAndFlush(user);

        assertThat(capture.toString().split("\n").length).isEqualTo(1);
        assertThat(capture.toString()).contains("insert into user");
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getUsername()).isEqualTo("John Doe");
        assertThat(user.getPassword()).isEqualTo("MySuperSecret");
        assertThat(user.getEmail()).isEqualTo("john.doe@email.com");
    }

}
