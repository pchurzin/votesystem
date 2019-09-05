package ru.pchurzin.votesystem.repository;

import org.springframework.stereotype.Repository;
import ru.pchurzin.votesystem.model.Restaurant;

import java.util.Collection;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {
    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant findById(int id) {
        return null;
    }

    @Override
    public Collection<Restaurant> findAll() {
        return null;
    }

    @Override
    public void removeById(int id) {

    }
}
