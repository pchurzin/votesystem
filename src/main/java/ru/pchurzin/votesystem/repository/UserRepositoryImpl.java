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
import ru.pchurzin.votesystem.model.User;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String TABLE_NAME = "users";
    private static final RowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> save(User user) {
        User newUser = new User(user);
        SqlParameterSource params = new BeanPropertySqlParameterSource(newUser);

        if (newUser.isNew()) {
            Number newKey = jdbcInsert.executeAndReturnKey(params);
            newUser.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate
                    .update("UPDATE " + TABLE_NAME + " SET name = :name, password = :password WHERE id=:id", params) == 0) {
                return Optional.empty();
            }
        }
        return Optional.of(newUser);
    }

    @Override
    public Optional<User> findById(int id) {
        List<User> users = namedParameterJdbcTemplate.query("SELECT * FROM " + TABLE_NAME + " WHERE id=:id",
                new MapSqlParameterSource("id", id), ROW_MAPPER);
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM " + TABLE_NAME, ROW_MAPPER);
    }

    @Override
    public boolean removeById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id=:id", params) > 0;
    }
}
