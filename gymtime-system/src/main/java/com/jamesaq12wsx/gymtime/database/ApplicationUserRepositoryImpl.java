package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ApplicationUserRepositoryImpl implements ApplicationUserRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public ApplicationUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findRecentUserByClubId(Long clubId) {

        String query = "select gtu.*, post.time \n" +
                "from post\n" +
                "join gym_time_user gtu on post.created_by = gtu.username\n" +
                "join fitness_club fc on post.club = fc.club_id\n" +
                "where fc.club_id = ?\n" +
                "group by gtu.user_uuid, post.time\n" +
                "order by post.time desc\n" +
                "limit 30";

        return jdbcTemplate.query(query, new Object[]{clubId}, shortUserDataRowMapper());

    }

    @Override
    public List<User> findRecentUserByClubIdExcludeUser(UUID clubId, String username) {

        String query = "select gtu.user_uuid, gtu.name, gtu.image_url\n" +
                "from post\n" +
                "join gym_time_user gtu on post.created_by = gtu.username\n" +
                "join fitness_club fc on post.location = fc.club_uid\n" +
                "where fc.club_uid = ?::int and gtu.username <> ?\n" +
                "group by gtu.user_uuid, gtu.name\n" +
                "order by gtu.created_at desc";

        return jdbcTemplate.query(query, new Object[]{clubId.toString(), username}, shortUserDataRowMapper());
    }

    public RowMapper<User> shortUserDataRowMapper(){
        return (resultSet, i) -> {
            String uuidStr = resultSet.getString("user_uuid");
            UUID userUuid = UUID.fromString(uuidStr);

            String idStr = resultSet.getString("user_id");
            Long id = Long.valueOf(idStr);
            String name = resultSet.getString("name");
            String imageUrl = resultSet.getString("image_url");

            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setImageUrl(imageUrl);

            return user;
        };
    }
}
