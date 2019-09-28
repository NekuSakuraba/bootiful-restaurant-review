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
public class RoleJpaTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testRoleMapping() {
        Role role = new Role(null, "ROLE_USER");

        capture.reset();
        testEntityManager.persistAndFlush(role);

        assertThat(capture.toString().split("\n").length).isEqualTo(1);
        assertThat(capture.toString()).contains("insert into role");
        assertThat(role.getId()).isGreaterThan(0L);
        assertThat(role.getName()).isEqualTo("ROLE_USER");
    }

}
