package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.exception.ApiException;
import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class ApplicationUserDaoImpl implements ApplicationUserDao {

    private final JdbcTemplate jdbcTemplate;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoImpl(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {

        String sql = "select *\n" +
                "from gym_time_user\n" +
                "where username = ?;";

        return Optional.of(
                jdbcTemplate.queryForObject(
                        sql,
                        new Object[]{username},
                        applicationUserRowMapper())
        );

    }

    @Override
    public boolean usernameExisted(String username) {
        String sql = "Select exists (" +
                " select 1 " +
                " from gym_time_user " +
                " where username = ? " +
                ")";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {username},
                Boolean.class);
    }

    private RowMapper<ApplicationUser> applicationUserRowMapper() {
        return (resultSet, i) -> {

            try{
                String uuidStr = resultSet.getString("user_uuid");
                UUID uuid = UUID.fromString(uuidStr);

                String userUsername = resultSet.getString("username");
                String encodePassword = resultSet.getString("password");
                String email = resultSet.getString("email");

                String roleString = resultSet.getString("role");
                ApplicationUserRole role = ApplicationUserRole.valueOf(roleString);

                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

                return new ApplicationUser(uuid,
                        userUsername,
                        encodePassword,
                        email,
                        role,
                        true,
                        true,
                        true,
                        true,
                        createdAt,
                        updatedAt);
            }catch (Exception e){
                throw e;
            }
        };
    }

    @Override
    public Optional<ApplicationUser> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ApplicationUser> getAll() {
        return null;
    }

    @Override
    public void save(ApplicationUser applicationUser) {
        String sql = "insert into gym_time_user(username, password, email, role)\n" +
                "values (?, ?, ?, ?::user_role);";

        jdbcTemplate.update(
                sql,
                new Object[]{
                        applicationUser.getUsername(),
                        passwordEncoder.encode(applicationUser.getPassword()),
                        applicationUser.getEmail(),
                        applicationUser.getRole().toString()});
    }

    @Override
    public void saveAll(List<? extends ApplicationUser> tList) {

    }

    @Override
    public void update(ApplicationUser applicationUser, String[] params) {

    }

    @Override
    public void delete(ApplicationUser applicationUser) {

    }
}
