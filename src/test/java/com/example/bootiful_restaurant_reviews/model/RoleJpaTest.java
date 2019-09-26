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
public class RoleJpaTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testUserMapping() {
        Role role = testEntityManager.persistFlushFind(new Role(null, "ROLE_USER"));

        assertThat(role.getId()).isGreaterThan(0L);
        assertThat(role.getName()).isEqualTo("ROLE_USER");

        Long id = role.getId();

        role.setName("ROLE_ADMIN");
        role = testEntityManager.persistFlushFind(role);

        assertThat(role.getId()).isEqualTo(id);
        assertThat(role.getName()).isEqualTo("ROLE_ADMIN");
    }

}
