package ru.pchurzin.votesystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuItemTest {

    @Test
    void menuItemsShouldBeEqual() {

        MenuItem mi1 = new MenuItem.Builder()
                .withId(1)
                .withTitle("title1")
                .withPrice(10000)
                .build();
        MenuItem mi2 = new MenuItem.Builder()
                .withId(1)
                .withTitle("title1")
                .withPrice(10000)
                .build();

        assertEquals(mi2, mi1);
    }

    @Test
    void copyingConstructorShouldCreateEqualObject() {
        MenuItem orig = new MenuItem.Builder()
                .withId(1)
                .withTitle("title1")
                .withPrice(999)
                .build();
        MenuItem copy = new MenuItem(orig);
        assertEquals(copy, orig);
    }

    @Test
    void negativePriceShouldThrowException() {
        MenuItem menuItem = new MenuItem();
        assertThrows(IllegalArgumentException.class, () -> menuItem.setPrice(-100));
        assertThrows(IllegalArgumentException.class,
                () -> new MenuItem.Builder().withPrice(-100).build());
    }
}
