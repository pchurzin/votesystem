package ru.pchurzin.votesystem.service;

import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.repository.RestaurantRepository;

import java.util.Collection;

public class VoteSystemServiceImpl implements VoteSystemService {

    private final RestaurantRepository restaurantRepository;

    public VoteSystemServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(int id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Collection<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public void removeRestaurantById(int id) {
        restaurantRepository.removeById(id);
    }
}
