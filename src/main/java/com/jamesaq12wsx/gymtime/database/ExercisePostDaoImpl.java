package com.jamesaq12wsx.gymtime.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ExercisePostDaoImpl implements ExercisePostDao {

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ExercisePostDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<ExercisePost> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ExercisePost> getAllMarksByUser(String username) {

        String sql = "select *\n" +
                "from exercise_post \n" +
                "where username = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{username},
                mapExerciseFromDb()
        );

    }

    @Override
    public List<ExercisePost> getAllPostsByUserWithYear(String year, String username) {

        String sql = "select *, fb.name as brand_name\n" +
                "from exercise_post as ep\n" +
                "join fitness_club as fc on fc.club_uid = ep.location\n" +
                "join fitness_brand as fb on fc.club_brand = fb.id \n" +
                "where username = ? and (select extract(year from date(ep.post_time) ))::varchar = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{username, year},
                mapExercisePostWithClubInfoFromDb()
        );

    }

    @Override
    public List<PostCount> getGymHourPost(UUID clubUuid, LocalDate date) {

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

    private RowMapper<ExercisePost> mapExerciseFromDb() {
        return (resultSet, i) -> {
            try {
                String uuidStr = resultSet.getString("post_uuid");
                UUID uuid = UUID.fromString(uuidStr);

                String username = resultSet.getString("username");

                String locationUuidStr = resultSet.getString("location");
                UUID locationUuid = locationUuidStr == null ? null : UUID.fromString(locationUuidStr);

                String privacy = resultSet.getString("privacy");

                LocalDateTime markTime = resultSet.getTimestamp("post_time").toLocalDateTime();

                String exercisesStr = resultSet.getString("exercises");

                PostExercise[] exercisesArr = mapper.readValue(exercisesStr, PostExercise[].class);

                List<PostExercise> exercises = Arrays.asList(exercisesArr);

                return new SimpleExercisePost(uuid, markTime, PostPrivacy.valueOf(privacy), locationUuid, exercises);

            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    private RowMapper<ExercisePost> mapExercisePostWithClubInfoFromDb() {
        return (resultSet, i) -> {
            try {
                String uuidStr = resultSet.getString("post_uuid");
                UUID uuid = UUID.fromString(uuidStr);

                String username = resultSet.getString("username");

                String clubName = resultSet.getString("club_name");

                String brandName = resultSet.getString("brand_name");

                String locationUuidStr = resultSet.getString("location");
                UUID locationUuid = locationUuidStr == null ? null : UUID.fromString(locationUuidStr);

                String privacy = resultSet.getString("privacy");

                LocalDateTime markTime = resultSet.getTimestamp("post_time").toLocalDateTime();

//                List<PostExercise> exercises = (List<PostExercise>) resultSet.getObject("exercises");

                String exercisesStr = resultSet.getString("exercises");

                PostExercise[] exercisesArr = mapper.readValue(exercisesStr, PostExercise[].class);

                List<PostExercise> exercises = Arrays.asList(exercisesArr);

                return new SimpleExercisePostWithClubInfo(uuid, markTime, PostPrivacy.valueOf(privacy), locationUuid, exercises, clubName, brandName);

            } catch (JsonProcessingException e) {
                return null;
            } catch (Exception e) {
                throw e;
            }
        };
    }

    @Override
    public List<ExercisePost> getAll() {
        return null;
    }

    @Override
    public void save(ExercisePost exercisePost) {
        String insertMarkSql = "insert into exercise_post(username, post_time, location, exercises, privacy)\n" +
                "select \n" +
                "       username, " +
                "mark_time, " +
                "case\n" +
                "   when (SELECT exists (SELECT 1 FROM fitness_club WHERE club_uid = location::uuid LIMIT 1))\n" +
                "       then location::uuid \n" +
                "   end,\n" +
                "exercises \n," +
                "       case\n" +
                "           when privacy = any(enum_range(null::post_privacy)::name[])\n" +
                "               then privacy::post_privacy\n" +
                "else" +
                "'PRIVATE'::post_privacy" +
                "           end\n" +
                "from (values (?,?,?,?::jsonb,?)) t(username, mark_time, location, exercises, privacy)";

        SimpleExercisePostAudit simpleExercisePostAudit = (SimpleExercisePostAudit) exercisePost;

        ObjectMapper mapper = new ObjectMapper();

        String exercisesJsonStr = "";

        try {
            exercisesJsonStr = mapper.writeValueAsString(simpleExercisePostAudit.getExercises());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jdbcTemplate.update(
                insertMarkSql,
                new Object[]{
                        simpleExercisePostAudit.getCreatedBy(),
                        simpleExercisePostAudit.getPostTime(),
                        simpleExercisePostAudit.getClubUuid(),
                        exercisesJsonStr,
                        simpleExercisePostAudit.getPrivacy().toString()});
    }

    @Override
    public void saveAll(List<? extends ExercisePost> tList) {

    }

    @Override
    public void update(ExercisePost exercisePost) {
        String sql = "update exercise_post\n" +
                "set post_time = pt, \n" +
                "location = case\n" +
                "   when (SELECT exists (SELECT 1 FROM fitness_club WHERE club_uid = club::uuid LIMIT 1))\n" +
                "       then club::uuid \n" +
                "   end,\n" +
                "exercises = exs \n," +
                "privacy = case\n" +
                "           when pri = any(enum_range(null::post_privacy)::name[])\n" +
                "               then pri::post_privacy\n" +
                "           else" +
                "               'PRIVATE'::post_privacy" +
                "           end,\n" +
                "updated_at = up\n" +
                "from (values (?,?,?::jsonb,?,?)) t(pt, club, exs, pri, up)" +
                "where post_uuid = ?::uuid";

        ObjectMapper mapper = new ObjectMapper();

        try {

            String exerciseJsonStr = mapper.writeValueAsString(exercisePost.getExercises());

            jdbcTemplate.update(sql, new Object[]{exercisePost.getPostTime(), exercisePost.getClubUuid(), exerciseJsonStr, exercisePost.getPrivacy().toString(), LocalDateTime.now(), exercisePost.getUuid().toString()});

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(UUID postUuid) {
        String sql = "delete from exercise_post\n" +
                "where post_uuid = ?::uuid";

        jdbcTemplate.update(sql, new Object[]{postUuid.toString()});
    }
}
