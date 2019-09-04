package ru.pchurzin.votesystem.service;

import ru.pchurzin.votesystem.model.Restaurant;

import java.util.Collection;

public interface VoteSystemService {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findRestaurantById(int id);

    Collection<Restaurant> findAllRestaurants();

    void removeRestaurantById(int id);

}
