package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import com.jamesaq12wsx.gymtime.model.SimpleExerciseAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ExerciseDaoImpl implements ExerciseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExerciseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Exercise> getSimpleAll() {

        String sql = "select * from exercise";

        return jdbcTemplate.query(sql, ((resultSet, i) -> {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String category = resultSet.getString("category");

            return new SimpleExercise(id, name, description, category);

        }));


    }

    @Override
    public Optional<Exercise> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Exercise> getAll() {

        String sql = "select * from exercise";

        return jdbcTemplate.query(sql, ((resultSet, i) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String category = resultSet.getString("category");
            String createdBy = resultSet.getString("created_by");
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedBy = resultSet.getTimestamp("updated_at").toLocalDateTime();

            return new SimpleExerciseAudit(id, name, description, category, createdBy, createdAt, updatedBy);
        }));

    }

    @Override
    public void save(Exercise exercise) {

    }

    @Override
    public void saveAll(List<? extends Exercise> tList) {

    }

    @Override
    public void update(Exercise exercise, String[] params) {

    }

    @Override
    public void delete(Exercise exercise) {

    }
}
