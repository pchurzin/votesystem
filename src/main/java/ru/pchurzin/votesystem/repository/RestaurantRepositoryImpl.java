package ru.pchurzin.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.pchurzin.votesystem.model.Restaurant;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private static final String TABLE_NAME = "restaurants";
    private static final RowMapper<Restaurant> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);
    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public RestaurantRepositoryImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Restaurant> save(Restaurant restaurant) {
        Restaurant newRestaurant = new Restaurant(restaurant);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", newRestaurant.getId());
        params.addValue("title", newRestaurant.getTitle());

        if (newRestaurant.isNew()) {
            Number newKey = jdbcInsert.executeAndReturnKey(params);
            newRestaurant.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate
                    .update("UPDATE " + TABLE_NAME + " SET title = :title WHERE id=:id", params) == 0) {
                return Optional.empty();
            }
        }
        return Optional.of(newRestaurant);
    }

    @Override
    public Optional<Restaurant> findById(int id) {
        List<Restaurant> restaurants = namedParameterJdbcTemplate.query("SELECT * FROM " + TABLE_NAME + " WHERE id=:id",
                new MapSqlParameterSource("id", id), ROW_MAPPER);
        return restaurants.size() == 1 ? Optional.of(restaurants.get(0)) : Optional.empty();
    }

    @Override
    public Collection<Restaurant> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM " + TABLE_NAME, ROW_MAPPER);
    }

    @Override
    public boolean removeById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id=:id", params) > 0;
    }
}
