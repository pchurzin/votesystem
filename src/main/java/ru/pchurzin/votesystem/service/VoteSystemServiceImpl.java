package ru.pchurzin.votesystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pchurzin.votesystem.model.MenuItem;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.repository.MenuItemRepository;
import ru.pchurzin.votesystem.repository.RestaurantRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class VoteSystemServiceImpl implements VoteSystemService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public VoteSystemServiceImpl(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
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
        menuItemRepository.findAllByRestaurantId(id);
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
}
