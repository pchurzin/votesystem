package ru.pchurzin.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.pchurzin.votesystem.model.MenuItem;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private static final String TABLE_NAME = "menu_items";
    private static final RowMapper<MenuItem> ROW_MAPPER = BeanPropertyRowMapper.newInstance(MenuItem.class);
    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MenuItemRepositoryImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<MenuItem> save(MenuItem menuItem) {
        MenuItem newMenuItem = new MenuItem(menuItem);
        SqlParameterSource params = new BeanPropertySqlParameterSource(newMenuItem);

        if (newMenuItem.isNew()) {
            Number newKey = jdbcInsert.executeAndReturnKey(params);
            newMenuItem.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate
                    .update("UPDATE " + TABLE_NAME + " SET title = :title, restaurant_id = :restaurantId, price = :price WHERE id=:id", params) == 0) {
                return Optional.empty();
            }
        }
        return Optional.of(newMenuItem);
    }

    @Override
    public Optional<MenuItem> findById(int id) {
        List<MenuItem> menuItems = namedParameterJdbcTemplate.query("SELECT * FROM " + TABLE_NAME + " WHERE id=:id",
                new MapSqlParameterSource("id", id), ROW_MAPPER);
        return menuItems.size() == 1 ? Optional.of(menuItems.get(0)) : Optional.empty();
    }

    @Override
    public Collection<MenuItem> findAllByRestaurantId(int restaurantId) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM " + TABLE_NAME + " WHERE restaurant_id = :id",
                new MapSqlParameterSource("id", restaurantId), ROW_MAPPER);
    }

    @Override
    public boolean removeById(int id) {
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id=:id",
                new MapSqlParameterSource("id", id)) > 0;
    }

    @Override
    public boolean removeByRestaurantId(int restaurantId) {
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE restaurant_id=:id",
                new MapSqlParameterSource("id", restaurantId)) > 0;
    }
}
