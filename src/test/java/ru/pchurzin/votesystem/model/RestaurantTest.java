package ru.pchurzin.votesystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Test
    void restaurantsShouldBeEqual() {
        Restaurant r1 = new Restaurant()
                .setId(1)
                .setTitle("title1");
        Restaurant r2 = new Restaurant()
                .setId(1)
                .setTitle("title1");
        assertEquals(r2, r1);
    }
}