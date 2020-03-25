package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.ExercisePostDetail;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ExercisePostDaoImpl implements ExercisePostDao {

    private final JdbcTemplate jdbcTemplate;

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
    public List<ExercisePostDetail> getAllPostsByUserWithYear(String year, String username) {

        String sql = "select *, fb.name as brand_name\n" +
                "from exercise_post as ep\n" +
                "join fitness_club as fc on fc.club_uid = ep.location\n" +
                "join fitness_brand as fb on fc.club_brand = fb.id \n" +
                "where username = ? and (select extract(year from date(ep.post_time) ))::varchar = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{username, year},
                mapExercisePostDetailFromDb()
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

    private RowMapper<PostCount> mapHourPostFromDb(){
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

                Map<String, String> exercises = (Map<String, String>) resultSet.getObject("exercises");

                return new ExercisePost(uuid, username, markTime, PostPrivacy.valueOf(privacy), locationUuid, exercises);

            } catch (Exception e) {
                throw e;
            }
        };
    }

    private RowMapper<ExercisePostDetail> mapExercisePostDetailFromDb() {
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

                Map<String, String> exercises = (Map<String, String>) resultSet.getObject("exercises");

                return new ExercisePostDetail(uuid, username, markTime, PostPrivacy.valueOf(privacy), locationUuid, exercises, clubName, brandName);

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
                "else" +
                "   null" +
                "   end,\n" +
                "exercises \n," +
                "       case\n" +
                "           when privacy = any(enum_range(null::post_privacy)::name[])\n" +
                "               then privacy::post_privacy\n" +
                "else" +
                "'PRIVATE'::post_privacy" +
                "           end\n" +
                "from (values (?,?,?,?,?)) t(username, mark_time, location, exercises, privacy)";

        jdbcTemplate.update(
                insertMarkSql,
                new Object[]{
                        exercisePost.getUsername(),
                        exercisePost.getPostTime(),
                        exercisePost.getClubUuid(),
                        exercisePost.getExercises(),
                        exercisePost.getPrivacy().toString()});
    }

    @Override
    public void saveAll(List<? extends ExercisePost> tList) {

    }

    @Override
    public void update(ExercisePost exercisePost, String[] params) {

    }

    @Override
    public void delete(ExercisePost exercisePost) {

    }
}
