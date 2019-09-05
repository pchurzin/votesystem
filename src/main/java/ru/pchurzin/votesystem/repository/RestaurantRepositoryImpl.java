package ru.pchurzin.votesystem.repository;

import org.springframework.stereotype.Repository;
import ru.pchurzin.votesystem.model.Restaurant;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {
    @Override
    public Optional<Restaurant> save(Restaurant restaurant) {
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Restaurant> findAll() {
        return Collections.emptySet();
    }

    @Override
    public boolean removeById(int id) {
        return false;
    }
}
