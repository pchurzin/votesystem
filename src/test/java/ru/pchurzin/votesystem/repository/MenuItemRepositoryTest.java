package ru.pchurzin.votesystem.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.pchurzin.votesystem.model.MenuItem;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(RepositoryTestConfig.class)
@Sql(scripts = {"classpath:restaurants-test-data.sql", "classpath:menuitems-test-data.sql"})
@Transactional
class MenuItemRepositoryTest {

    @Autowired
    protected MenuItemRepository repository;

    @Test
    void checkDbState() {
        assertEquals(2, repository.findAllByRestaurantId(100).size());
        assertEquals(2, repository.findAllByRestaurantId(101).size());
    }

    @Test
    void shouldSaveNewMenuItem() {
        int initialSize = repository.findAllByRestaurantId(100).size();
        MenuItem newMenuItem = new MenuItem.Builder()
                .withRestaurantId(100)
                .withPrice(222)
                .withTitle("some item")
                .build();
        Optional<MenuItem> savedItem = repository.save(newMenuItem);
        assertTrue(savedItem.isPresent());
        assertEquals(initialSize + 1, repository.findAllByRestaurantId(100).size());
        assertFalse(savedItem.get().isNew());
    }

    @Test
    void shouldReturnExistingMenuItem() {
        assertTrue(repository.findById(200).isPresent());
    }

    @Test
    void shouldUpdateExistingMenuItem() {
        Optional<MenuItem> menuItem = repository.findById(200);
        assertTrue(menuItem.isPresent());
        MenuItem itemToUpdate = menuItem.get();
        MenuItem updatedItem = new MenuItem(itemToUpdate);
        updatedItem.setTitle(itemToUpdate.getTitle() + "updated");
        Optional<MenuItem> savedItem = repository.save(updatedItem);
        assertTrue(savedItem.isPresent());
        assertEquals(itemToUpdate.getId(), savedItem.get().getId());
        assertEquals(updatedItem, savedItem.get());
    }

    @Test
    void shouldRemoveExistingMenuItem() {
        int size = repository.findAllByRestaurantId(100).size();
        assertTrue(repository.removeById(200));
        assertEquals(size - 1, repository.findAllByRestaurantId(100).size());
        assertFalse(repository.findById(200).isPresent());
    }

    @Test
    void shouldNotSaveMenuItemWithWrongRestaurantId() {
        MenuItem noRestaurantItem = new MenuItem.Builder()
                .withTitle("no restaurant")
                .withPrice(333)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(noRestaurantItem));

        MenuItem nonExistingRestaurantMenuItem = new MenuItem.Builder()
                .withPrice(777)
                .withRestaurantId(999)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(nonExistingRestaurantMenuItem));
    }

    @Test
    void shouldRemoveMenuItemsByRestaurantId() {
        assertTrue(repository.removeByRestaurantId(100));
        assertEquals(0, repository.findAllByRestaurantId(100).size());
    }

    @Test
    void shouldReturnEmptyCollection() {
        Collection<MenuItem> allByRestaurantId = repository.findAllByRestaurantId(999);
        assertNotNull(allByRestaurantId);
        assertEquals(0, allByRestaurantId.size());
    }
}