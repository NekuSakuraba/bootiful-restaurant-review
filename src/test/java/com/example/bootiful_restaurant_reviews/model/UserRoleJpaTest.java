package com.example.bootiful_restaurant_reviews.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRoleJpaTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private TestEntityManager testEntityManager;

    private User john;
    private User jane;
    private Role roleAdmin;
    private Role roleUser;

    @Before
    public void setUp() {
        john = new User(null, "John Doe", "MySuperSecret", "john.doe@example.com");
        jane = new User(null, "Jane Doe", "MySuperSecret", "jane.doe@example.com");

        roleAdmin = new Role(null, "ROLE_ADMIN");
        roleUser = new Role(null, "ROLE_USER");

        testEntityManager.persistAndFlush(john);
        testEntityManager.persistAndFlush(jane);
        testEntityManager.persistAndFlush(roleAdmin);
        testEntityManager.persistAndFlush(roleUser);
    }

    @Test
    public void testUserRoleMapping() {
        capture.reset();

        john.addRole(roleAdmin)
                .addRole(roleUser);
        jane.addRole(roleUser);
        testEntityManager.flush();

        Arrays.stream(capture.toString().split("\n"))
                .forEach(line -> assertThat(line).contains("insert into users_roles"));

        capture.reset();
        john.removeRole(roleUser);
        testEntityManager.flush();

        assertThat(capture.toString().split("\n").length).isEqualTo(1);
        assertThat(capture.toString()).contains("delete from users_roles where user_id=? and role_id=?");
    }

}
