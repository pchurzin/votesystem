package ru.pchurzin.votesystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void usersShouldBeEqual() {
        User user1 = new User.Builder()
                .withName("name")
                .withPassword("password")
                .withId(1)
                .build();

        User user2 = new User.Builder()
                .withName("name")
                .withPassword("password")
                .withId(1)
                .build();

        assertEquals(user1, user2);
    }

    @Test
    void copyConstructorShouldCreateEqualObjects() {
        User user1 = new User.Builder()
                .withName("name")
                .withPassword("password")
                .withId(1)
                .build();
        User user2 = new User(user1);
        assertEquals(user1, user2);
    }

    @Test
    void builderShouldBuildEqualObject() {
        User user1 = new User.Builder()
                .withName("name")
                .withPassword("password")
                .withId(1)
                .build();

        User user2 = new User.Builder(user1).build();
        assertEquals(user1, user2);
    }
}