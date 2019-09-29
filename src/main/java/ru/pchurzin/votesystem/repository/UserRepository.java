package ru.pchurzin.votesystem.repository;

import ru.pchurzin.votesystem.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    /**
     * inserts or updates user
     *
     * @param user <code>User</code> to be inserted or updated
     * @return <code>Optional</code> with updated user or empty if operation didn't perform action
     */
    Optional<User> save(User user);

    /**
     * fetch <code>User</code> by it's id
     *
     * @param id the id of the <code>User</code>
     * @return Optional with found <code>User</code> or empty if no user with id found
     */
    Optional<User> findById(int id);

    /**
     * fetch all users
     *
     * @return <code>Collection</code> of <code>User</code>s or empty <code>Collection</code>
     */
    Collection<User> findAll();

    /**
     * deletes user by id
     *
     * @param id the id of the <code>User</code> to be deleted
     * @return if the user has been removed
     */
    boolean removeById(int id);
}
