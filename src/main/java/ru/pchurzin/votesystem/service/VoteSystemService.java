package ru.pchurzin.votesystem.service;

import ru.pchurzin.votesystem.model.Restaurant;

import java.util.Collection;
import java.util.Optional;

public interface VoteSystemService {

    /**
     * inserts or updates restaurant
     *
     * @param restaurant <code>Restaurant</code> to be inserted or updated
     * @return <code>Optional</code> with updated restaurant or empty if operation didn't perform action
     */
    Optional<Restaurant> saveRestaurant(Restaurant restaurant);

    /**
     * fetch <code>Restaurant</code> by it's id
     *
     * @param id the id of the <code>Restaurant</code>
     * @return Optional with found <code>Restaurant</code> or empty if no restaurant with id found
     */
    Optional<Restaurant> findRestaurantById(int id);

    /**
     * fetch all restaurants
     *
     * @return <code>Collection</code> of <code>Restaurant</code>s or empty <code>Collection</code>
     */
    Collection<Restaurant> findAllRestaurants();

    /**
     * deletes restaurant by id
     *
     * @param id the id of the <code>Restaurant</code> to be deleted
     * @return <code>true</code> if the restaurant has been removed
     */
    boolean removeRestaurantById(int id);

}
