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
import ru.pchurzin.votesystem.model.Vote;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    private static final String TABLE_NAME = "votes";
    private static final RowMapper<Vote> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Vote.class);
    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public VoteRepositoryImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Vote> save(Vote vote) {
        Vote newVote = new Vote(vote);
        SqlParameterSource params = new BeanPropertySqlParameterSource(newVote);

        if (newVote.isNew()) {
            Number newKey = jdbcInsert.executeAndReturnKey(params);
            newVote.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate
                    .update("UPDATE " + TABLE_NAME + " SET user_id = :userId, restaurant_id = :restaurantId, date = :date WHERE id=:id", params) == 0) {
                return Optional.empty();
            }
        }
        return Optional.of(newVote);
    }

    @Override
    public Optional<Vote> findByUserIdAndDate(int userId, LocalDate date) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("date", date);
        List<Vote> votes = namedParameterJdbcTemplate.query(
                "SELECT * FROM " + TABLE_NAME + " WHERE user_id = : userId AND date = :date",
                params, ROW_MAPPER);
        return votes.size() == 1 ? Optional.of(votes.get(0)) : Optional.empty();
    }

    @Override
    public Collection<Vote> findByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("restaurantId", restaurantId);
        params.addValue("date", date);
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM " + TABLE_NAME + " WHERE restaurant_id = : restaurantId AND date = :date",
                params, ROW_MAPPER);
    }

    @Override
    public boolean removeByUserIdAndDate(int userId, LocalDate date) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("date", date);
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME +
                " WHERE user_id = :userId AND date = :date", params) > 0;
    }

    @Override
    public boolean removeAllForUser(int userId) {
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME +
                " WHERE user_id = :userId", new MapSqlParameterSource("userId", userId)) > 0;
    }

    @Override
    public boolean removeAllForRestaurant(int restaurantId) {
        return namedParameterJdbcTemplate.update("DELETE FROM " + TABLE_NAME +
                        " WHERE restaurant_id = :restaurantId",
                new MapSqlParameterSource("restaurantId", restaurantId)) > 0;
    }
}
