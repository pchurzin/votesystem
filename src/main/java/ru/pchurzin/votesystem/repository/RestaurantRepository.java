package ru.pchurzin.votesystem.repository;

import ru.pchurzin.votesystem.model.Restaurant;

import java.util.Collection;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(int id);

    Collection<Restaurant> findAll();

    void removeById(int id);
}
