package ru.pchurzin.votesystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Test
    void restaurantsShouldBeEqual() {
        Restaurant r1 = new Restaurant.Builder()
                .withId(1)
                .withTitle("title1")
                .build();
        Restaurant r2 = new Restaurant.Builder()
                .withId(1)
                .withTitle("title1")
                .build();
        assertEquals(r2, r1);
    }

    @Test
    void copyingConstructorShouldCreateEqualObject() {
        Restaurant orig = new Restaurant.Builder()
                .withId(1)
                .withTitle("title1")
                .build();
        Restaurant copy = new Restaurant(orig);
        assertEquals(copy, orig);
    }
}