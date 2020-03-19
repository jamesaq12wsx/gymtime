package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.auth.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.ExerciseMark;
import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ExerciseMarkDaoImpl implements ExerciseMarkDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExerciseMarkDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<ExerciseMark> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ExerciseMark> getAllMarksByUser(String username) {

        String sql = "select *\n" +
                "from exercise_mark \n" +
                "where username = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{username},
                mapExerciseFromDb()
        );

    }

    private RowMapper<ExerciseMark> mapExerciseFromDb() {
        return (resultSet, i) -> {
            try {
                String uuidStr = resultSet.getString("mark_uuid");
                UUID uuid = UUID.fromString(uuidStr);

                String username = resultSet.getString("username");

                String locationUuidStr = resultSet.getString("location");
                UUID locationUuid = locationUuidStr == null ? null : UUID.fromString(locationUuidStr);

                LocalDateTime markTime = resultSet.getTimestamp("mark_time").toLocalDateTime();

                Map<String, String> exercises = (Map<String, String>) resultSet.getObject("exercises");

                return new ExerciseMark(uuid, username, markTime, locationUuid, exercises);

            } catch (Exception e) {
                throw e;
            }
        };
    }

    @Override
    public List<ExerciseMark> getAll() {
        return null;
    }

    @Override
    public void save(ExerciseMark exerciseMark) {
        String insertMarkSql = "insert into exercise_mark(username, mark_time, location, exercises)\n" +
                "select \n" +
                "       username, " +
                "mark_time, " +
                "case\n" +
                "   when (SELECT exists (SELECT 1 FROM fitness_club WHERE club_uid = location::uuid LIMIT 1))\n" +
                "       then location::uuid \n" +
                "else" +
                "   null" +
                "   end,\n" +
                "exercises \n" +
                "from (values (?,?,?,?)) t(username, mark_time, location, exercises)";

        jdbcTemplate.update(
                insertMarkSql,
                new Object[]{
                        exerciseMark.getUsername(),
                        exerciseMark.getMarkTime(),
                        exerciseMark.getClubUuid(),
                        exerciseMark.getExercises()});
    }

    @Override
    public void saveAll(List<? extends ExerciseMark> tList) {

    }

    @Override
    public void update(ExerciseMark exerciseMark, String[] params) {

    }

    @Override
    public void delete(ExerciseMark exerciseMark) {

    }
}
