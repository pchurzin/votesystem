package ru.pchurzin.votesystem.service;

import ru.pchurzin.votesystem.model.MenuItem;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.model.User;
import ru.pchurzin.votesystem.model.Vote;

import java.time.LocalDate;
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

    /**
     * inserts or updates menu item
     *
     * @param menuItem <code>MenuItem</code> to be inserted or updated
     * @return <code>Optional</code> with updated menu item or empty if operation didn't perform action
     */
    Optional<MenuItem> saveMenuItem(MenuItem menuItem);

    /**
     * fetch <code>MenuItem</code> by it's id
     *
     * @param id the id of the <code>MenuItem</code>
     * @return Optional with found <code>MenuItem</code> or empty if no menu item with id was found
     */
    Optional<MenuItem> findMenuItemById(int id);

    /**
     * fetch all menu items for the restaurant with provided id
     *
     * @param restaurantId the id of the restaurant
     * @return <code>Collection</code> of <code>MenuItem</code>s or empty <code>Collection</code>
     */
    Collection<MenuItem> findAllMenuItemsByRestaurantId(int restaurantId);

    /**
     * deletes menuItem by id
     *
     * @param id the id of the <code>MenuItem</code> to be deleted
     * @return <code>true</code> if the menu item has been removed
     */
    boolean removeMenuItemById(int id);

    /**
     * inserts or updates user
     *
     * @param user <code>User</code> to be inserted or updated
     * @return <code>Optional</code> with updated user or empty if operation didn't perform action
     */
    Optional<User> saveUser(User user);

    /**
     * fetch <code>User</code> by it's id
     *
     * @param id the id of the <code>User</code>
     * @return Optional with found <code>User</code> or empty if no user with id found
     */
    Optional<User> findUserById(int id);

    /**
     * fetch all users
     *
     * @return <code>Collection</code> of <code>User</code>s or empty <code>Collection</code>
     */
    Collection<User> findAllUsers();

    /**
     * deletes user by id
     *
     * @param id the id of the <code>User</code> to be deleted
     * @return <code>true</code> if the user has been removed
     */
    boolean removeUserById(int id);

    /**
     * inserts or updates vote
     * @param vote a vote to be inserted or updated
     * @return <code>Optional</code> with updated vote or empty if operation didn't perform action
     */
    Optional<Vote> saveVote(Vote vote);

    /**
     * fetch <code>Vote</code> by it's id
     *
     * @param id the id of the <code>Vote</code>
     * @return Optional with found <code>Vote</code> or empty if no vote with the id was found
     */
    Optional<Vote> findVoteById(int id);

    /**
     * fetch vote by specified user on specified date
     * @param userId user id
     * @param date date
     * @return vote by specified user on specified date or empty if no such vote exists
     */
    Optional<Vote> findVoteByUserIdAndDate(int userId, LocalDate date);

    /**
     * fetch all votes for specified restaurant on specified date
     * @param restaurantId restaurant id
     * @param date date
     * @return <code>Collection</code> of <code>Vote</code> for specified restaurant on specified date or empty if no such vote exists
     */
    Collection<Vote> findVotesByRestaurantIdAndDate(int restaurantId, LocalDate date);

    /**
     * removes vote by specified user on specified date
     * @param userId user id
     * @param date date
     * @return if vote has been removed
     */
    boolean removeVoteByUserIdAndDate(int userId, LocalDate date);

    /**
     * removes all votes by specified user
     * @param userId user id
     * @return if votes have been removed
     */
    boolean removeAllVotesForUser(int userId);

    /**
     * removes all votes for specified restaurant
     * @param restaurantId restaurant id
     * @return if votes have been removed
     */
    boolean removeAllVotesForRestaurant(int restaurantId);

    /**
     * deletes vote by id
     *
     * @param id the id of the <code>Vote</code> to be deleted
     * @return <code>true</code> if the vote has been removed
     */
    boolean removeVoteById(int id);

}
