package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
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

        String sql = "select *, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club\n" +
                "join fitness_brand as fb on fitness_club.club_brand = fb.id\n" +
                "join country as c on c.id = fb.country\n" +
                "where club_uid = ?::uuid";

        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql,
                        new Object[]{id.toString()},
                        mapFitnessClubsFromDb()
                ));

    }

    @Override
    public List<FitnessClub> getAll() {

        String sql = "select *, sqrt( power(34.134267 - latitude, 2) + power(-118.106107 - longitude, 2)) * 111 as distance, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club as fc\n" +
                "join fitness_brand as fb on fc.club_brand = fb.id\n" +
                "join country as c on c.id = fb.country\n" +
                "limit 50;";

        return jdbcTemplate.query(sql, mapFitnessClubsFromDb());

    }

    @Override
    public List<FitnessClub> getClubsWithLatAndLon(double latitude, double longitude) {

        String sql = "select *, sqrt( power(? - latitude, 2) + power(? - longitude, 2)) * 111 as distance, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club as fc\n" +
                "join fitness_brand as fb on fc.club_brand = fb.id\n" +
                "join country as c on c.id = fb.country\n" +
                "order by distance\n" +
                "limit 50";

        String sql2 = "select *, sqrt( power(? - latitude, 2) + power(? - longitude, 2)) * 111 as distance, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club\n" +
                "join fitness_brand as fb on fitness_club.club_brand = fb.id\n" +
                "join country c on fb.country = c.id\n" +
                "order by distance\n" +
                "limit 50";

        return jdbcTemplate.query(
                sql2,
                new Object[]{
                        latitude, longitude
                },
                mapFitnessClubsFromDb());

    }

    @Override
    public boolean exist(UUID clubUuid) {
        String sql = "Select exists (" +
                " select 1 " +
                " from fitness_club " +
                " where club_uid = ?::uuid \n" +
                ")";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {clubUuid.toString()},
                Boolean.class);
    }

    public Optional<FitnessClub> getFitnessByUuid(UUID uuid) {

        String sql = "select *, sqrt( power(? - latitude, 2) + power(? - longitude, 2)) * 111 as distance, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club\n" +
                "join fitness_brand as fb on fitness_club.club_brand = fb.id\n" +
                "join country as c on c.id = fb.country\n" +
                "where club_uid = ?::uuid";

        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql,
                        new Object[]{uuid.toString()},
                        mapFitnessClubsFromDb()
                ));

    }

    private RowMapper<FitnessClub> mapFitnessClubsFromDb() {
        return (resultSet, i) -> {
            String uuidStr = resultSet.getString("club_uid");
            UUID clubUid = UUID.fromString(uuidStr);

            int brandId = resultSet.getInt("club_brand");
            String brandName = resultSet.getString("brand_name");

            int id = resultSet.getInt("club_id");
            String name = resultSet.getString("club_name");

            double latitude = resultSet.getDouble("latitude");
            double longitude = resultSet.getDouble("longitude");

            String homeUrl = resultSet.getString("club_home_url");
            String zipCode = resultSet.getString("zip_code");
            String address = resultSet.getString("address");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            String countryName = resultSet.getString("country_name");

            Map<String, String> openHours = (Map<String, String>) resultSet.getObject("open_hours");

            return new SimpleFitnessClub(clubUid, brandId, brandName, name, id, latitude, longitude, address, city, state, zipCode, countryName, homeUrl, openHours);
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
