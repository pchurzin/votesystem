package ru.pchurzin.votesystem.repository;

import ru.pchurzin.votesystem.model.Vote;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface VoteRepository {

    /**
     * inserts or updates vote
     * @param vote a vote to be inserted or updated
     * @return <code>Optional</code> with updated vote or empty if operation didn't perform action
     */
    Optional<Vote> save(Vote vote);

    /**
     * fetch <code>Vote</code> by it's id
     *
     * @param id the id of the <code>Vote</code>
     * @return Optional with found <code>Vote</code> or empty if no vote with the id was found
     */
    Optional<Vote> findById(int id);

    /**
     * fetch vote by specified user on specified date
     * @param userId user id
     * @param date date
     * @return vote by specified user on specified date or empty if no such vote exists
     */
    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    /**
     * fetch all votes for specified restaurant on specified date
     * @param restaurantId restaurant id
     * @param date date
     * @return <code>Collection</code> of <code>Vote</code> for specified restaurant on specified date or empty if no such vote exists
     */
    Collection<Vote> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    /**
     * removes vote by specified user on specified date
     * @param userId user id
     * @param date date
     * @return if vote has been removed
     */
    boolean removeByUserIdAndDate(int userId, LocalDate date);

    /**
     * removes all votes by specified user
     * @param userId user id
     * @return if votes have been removed
     */
    boolean removeAllForUser(int userId);

    /**
     * removes all votes for specified restaurant
     * @param restaurantId restaurant id
     * @return if votes have been removed
     */
    boolean removeAllForRestaurant(int restaurantId);

    /**
     * deletes vote by id
     *
     * @param id the id of the <code>Vote</code> to be deleted
     * @return <code>true</code> if the vote has been removed
     */
    boolean removeById(int id);

}
