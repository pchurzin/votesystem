package ru.pchurzin.votesystem.repository;

import ru.pchurzin.votesystem.model.MenuItem;

import java.util.Collection;
import java.util.Optional;

public interface MenuItemRepository {

    /**
     * inserts or updates menuItem
     *
     * @param menuItem <code>MenuItem</code> to be inserted or updated
     * @return <code>Optional</code> with updated menu item or empty if the operation didn't perform action
     */
    Optional<MenuItem> save(MenuItem menuItem);

    /**
     * fetch <code>MenuItem</code> by it's id
     *
     * @param id the id of the <code>MenuItem</code>
     * @return Optional with found <code>MenuItem</code> or empty if no menu item with id found
     */
    Optional<MenuItem> findById(int id);

    /**
     * fetch all menu items for the restaurant with specified id
     *
     * @return <code>Collection</code> of <code>MenuItem</code>s or empty <code>Collection</code>
     */
    Collection<MenuItem> findAllByRestaurantId(int restaurantId);

    /**
     * deletes menu item by id
     *
     * @param id the id of the <code>MenuItem</code> to be deleted
     * @return if the menu item has been removed
     */
    boolean removeById(int id);

    /**
     * deletes all menu items for restaurant with specified id
     *
     * @param restaurantId the id of the <code>Restaurant</code> which menu items to be deleted
     * @return if the menu items has been removed
     */
    boolean removeByRestaurantId(int restaurantId);
}
