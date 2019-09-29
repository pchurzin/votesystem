package ru.pchurzin.votesystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pchurzin.votesystem.model.MenuItem;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.model.User;
import ru.pchurzin.votesystem.model.Vote;
import ru.pchurzin.votesystem.repository.MenuItemRepository;
import ru.pchurzin.votesystem.repository.RestaurantRepository;
import ru.pchurzin.votesystem.repository.UserRepository;
import ru.pchurzin.votesystem.repository.VoteRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class VoteSystemServiceImpl implements VoteSystemService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public VoteSystemServiceImpl(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Optional<Restaurant> saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Restaurant> findRestaurantById(int id) {
        return restaurantRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public boolean removeRestaurantById(int id) {
        menuItemRepository.removeByRestaurantId(id);
        voteRepository.removeAllForRestaurant(id);
        return restaurantRepository.removeById(id);
    }

    @Override
    public Optional<MenuItem> saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuItem> findMenuItemById(int id) {
        return menuItemRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<MenuItem> findAllMenuItemsByRestaurantId(int restaurantId) {
        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public boolean removeMenuItemById(int id) {
        return menuItemRepository.removeById(id);
    }

    @Override
    public Optional<User> saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean removeUserById(int id) {
        voteRepository.removeAllForUser(id);
        return userRepository.removeById(id);
    }

    @Override
    public Optional<Vote> saveVote(Vote vote) {
        voteRepository.removeByUserIdAndDate(vote.getUserId(), vote.getDate());
        return voteRepository.save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vote> findVoteById(int id) {
        return voteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vote> findVoteByUserIdAndDate(int userId, LocalDate date) {
        return voteRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Vote> findVotesByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return voteRepository.findByRestaurantIdAndDate(restaurantId, date);
    }

    @Override
    public boolean removeVoteByUserIdAndDate(int userId, LocalDate date) {
        return voteRepository.removeByUserIdAndDate(userId, date);
    }

    @Override
    public boolean removeAllVotesForUser(int userId) {
        return voteRepository.removeAllForUser(userId);
    }

    @Override
    public boolean removeAllVotesForRestaurant(int restaurantId) {
        return voteRepository.removeAllForRestaurant(restaurantId);
    }
}
