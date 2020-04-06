package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.PostCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class PostCountRepositoryImpl implements PostCountRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostCountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PostCount> findAllByClub(UUID clubUuid, LocalDate date) {

        String sql = "select date_trunc('hour', post_time + interval '1 hour') as date_time, count(*)\n" +
                "from exercise_post as ep\n" +
                "where location = ?::uuid and date(post_time) = date(?)" +
                "group by date_time";

        return jdbcTemplate.query(
                sql,
                new Object[]{clubUuid.toString(), date},
                mapHourPostFromDb()
        );

    }

    private RowMapper<PostCount> mapHourPostFromDb() {
        return (resultSet, i) -> {

            LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();

            int count = resultSet.getInt("count");

            return new PostCount(dateTime, count);

        };
    }
}