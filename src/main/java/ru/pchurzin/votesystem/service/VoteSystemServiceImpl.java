package ru.pchurzin.votesystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pchurzin.votesystem.model.Restaurant;
import ru.pchurzin.votesystem.repository.RestaurantRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class VoteSystemServiceImpl implements VoteSystemService {

    private final RestaurantRepository restaurantRepository;

    public VoteSystemServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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
        return restaurantRepository.removeById(id);
    }
}
