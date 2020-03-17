package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FitnessClubDaoImpl implements FitnessClubDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public FitnessClubDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Optional<FitnessClub> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<FitnessClub> getAll() {

        String sql = "select *, sqrt( power(34.134267 - latitude, 2) + power(-118.106107 - longitude, 2)) * 111 as distance\n" +
                "from fitness_club\n" +
                "limit 50;";

        return jdbcTemplate.query(sql, mapFitnessClubsFromDb());

    }

    @Override
    public List<FitnessClub> getClubsWithLatAndLon(double latitude, double longitude) {

        String sql = "select fc.*, sqrt( power(? - latitude, 2) + power(? - longitude, 2)) * 111 as distance\n" +
                "from fitness_club as fc\n" +
                "order by sqrt( power(? - latitude, 2) + power(? - longitude, 2))\n" +
                "limit 50";

        return jdbcTemplate.query(
                sql,
                new Object[]{
                        latitude, longitude, latitude, longitude
                },
                mapFitnessClubsFromDb());

    }

    @Override
    public FitnessClubDetail getFitnessByUuid(UUID uuid) {

        String sql = "select *\n" +
                "from fitness_club\n" +
                "where club_uid = ?::uuid";

        return jdbcTemplate.queryForObject(sql,
                                    new Object[]{uuid.toString()},
                (resultSet, i) -> {
                    String uuidStr = resultSet.getString("club_uid");
                    UUID clubUid = UUID.fromString(uuidStr);

                    String brandStr = resultSet.getString("club_brand");
                    GymBrand brand = GymBrand.valueOf(brandStr);

                    int id = resultSet.getInt("club_id");
                    String name = resultSet.getString("club_name");

                    double latitude = resultSet.getDouble("latitude");
                    double longitude = resultSet.getDouble("longitude");

                    String homeUrl = resultSet.getString("club_home_url");
                    String zipCode = resultSet.getString("zip_code");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");

                    Map<String, String> openHours = (Map<String, String>) resultSet.getObject("open_hours");

                    switch (brand) {
                        case LA_FITNESS:
                            return new LaFitnessClubDetail(clubUid, id, latitude, longitude, name, address, city, state, zipCode, homeUrl, openHours, null, null, null);
                        default:
                            return null;
                    }
                });

    }

    private RowMapper<FitnessClub> mapFitnessClubsFromDb() {
        return (resultSet, i) -> {
            String uuidStr = resultSet.getString("club_uid");
            UUID clubUid = UUID.fromString(uuidStr);

            String brandStr = resultSet.getString("club_brand");
            GymBrand brand = GymBrand.valueOf(brandStr);

            int id = resultSet.getInt("club_id");
            String name = resultSet.getString("club_name");

            double latitude = resultSet.getDouble("latitude");
            double longitude = resultSet.getDouble("longitude");

            String homeUrl = resultSet.getString("club_home_url");
            String zipCode = resultSet.getString("zip_code");
            String address = resultSet.getString("address");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");

            Map<String, String> openHours = (Map<String, String>) resultSet.getObject("open_hours");

            String distance = resultSet.getString("distance");

            switch (brand) {
                case LA_FITNESS:
                    return new LaFitnessClub(clubUid, id, latitude, longitude, name, address, city, state, zipCode, homeUrl, openHours, distance);
                default:
                    return null;
            }
        };
    }

    @Override
    public void save(FitnessClub fitnessClub) {

    }

    @Override
    public void saveAll(List<? extends FitnessClub> tList) {

    }

    @Override
    public void update(FitnessClub fitnessClub, String[] params) {

    }

    @Override
    public void delete(FitnessClub fitnessClub) {

    }
}
