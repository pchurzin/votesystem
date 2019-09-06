package ru.pchurzin.votesystem.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.pchurzin.votesystem.model.Restaurant;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(RepositoryTestConfig.class)
@Sql(scripts = "classpath:restaurants-test-data.sql")
@Transactional
class RestaurantRepositoryTest {

    @Autowired
    protected RestaurantRepository repository;

    @Test
    void checkDbState() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldSaveNewRestaurant() {
        int initialSize = repository.findAll().size();

        Restaurant r = new Restaurant().setTitle("title");
        Optional<Restaurant> savedRestaurant = repository.save(r);
        int newSize = repository.findAll().size();
        assertEquals(initialSize + 1, newSize);
        assertTrue(savedRestaurant.isPresent());
        assertNotNull(savedRestaurant.get().getId());
    }

    @Test
    void shouldReturnExistingRestaurants() {
        Collection<Restaurant> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    @Sql(statements = "TRUNCATE TABLE restaurants")
    void shouldReturnEmptyCollection() {
        Collection<Restaurant> all = repository.findAll();
        assertNotNull(all);
        assertEquals(0, all.size());
    }

    @Test
    void shouldUpdateExistingRestaurant() {
        Optional<Restaurant> r = repository.findById(100);
        assertTrue(r.isPresent());
        Restaurant restaurant = r.get();
        String newTitle = restaurant.getTitle() + "(updated)";
        restaurant.setTitle(newTitle);
        Optional<Restaurant> updatedRestaurant = repository.save(restaurant);
        assertTrue(updatedRestaurant.isPresent());
        assertEquals(newTitle, updatedRestaurant.get().getTitle());
    }

    @Test
    void shouldDeleteRestaurant() {
        int initialSize = repository.findAll().size();
        assertTrue(repository.removeById(100));
        assertEquals(initialSize - 1, repository.findAll().size());
        assertFalse(repository.findById(100).isPresent());
    }
}