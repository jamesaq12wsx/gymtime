package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.ShortUserData;
import com.jamesaq12wsx.gymtime.model.UserData;
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
    public List<? extends UserData> findRecentUserByClubId(UUID clubId) {

        String query = "select gtu.user_uuid, gtu.name, gtu.image_url\n" +
                "from post\n" +
                "join gym_time_user gtu on post.created_by = gtu.email\n" +
                "join fitness_club fc on post.location = fc.club_uid\n" +
                "where fc.club_uid = ?::uuid\n" +
                "group by gtu.user_uuid, gtu.image_url, gtu.name\n" +
                "order by gtu.created_at desc";

        return jdbcTemplate.query(query, new Object[]{clubId.toString()}, shortUserDataRowMapper());

    }

    @Override
    public List<? extends UserData> findRecentUserByClubIdExcludeUser(UUID clubId, String username) {

        String query = "select gtu.user_uuid, gtu.name, gtu.image_url\n" +
                "from post\n" +
                "join gym_time_user gtu on post.created_by = gtu.email\n" +
                "join fitness_club fc on post.location = fc.club_uid\n" +
                "where fc.club_uid = ?::uuid and gtu.email <> ?\n" +
                "group by gtu.user_uuid, gtu.name\n" +
                "order by gtu.created_at desc;";

        return jdbcTemplate.query(query, new Object[]{clubId.toString(), username}, shortUserDataRowMapper());
    }

    public RowMapper<ShortUserData> shortUserDataRowMapper(){
        return (resultSet, i) -> {
            String idStr = resultSet.getString("user_uuid");
            UUID userUuid = UUID.fromString(idStr);
            String name = resultSet.getString("name");
            String imageUrl = resultSet.getString("image_url");

            return new ShortUserData(userUuid, name, imageUrl);
        };
    }
}
