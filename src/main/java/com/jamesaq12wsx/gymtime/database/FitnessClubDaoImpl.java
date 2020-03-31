package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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

        String sql = "select *, fb.name as brand_name, fb.id brand_id, c.id country_id, c.name as country_name\n" +
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

        String sql = "select *, fb.id brand_id, fb.name as brand_name, c.id country_id, c.name as country_name\n" +
                "from fitness_club as fc\n" +
                "join fitness_brand as fb on fc.club_brand = fb.id\n" +
                "join country as c on c.id = fb.country\n";

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
                new Object[]{clubUuid.toString()},
                Boolean.class);
    }

    @Override
    public Optional<FitnessClub> getClubWithUserPost(UUID clubUuid, String username) throws SQLException {
        String getClubDetailSql = "select *, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club\n" +
                "join fitness_brand as fb on fitness_club.club_brand = fb.id\n" +
                "join country as c on c.id = fb.country\n" +
                "where club_uid = ?::uuid";

        FitnessClub result = jdbcTemplate.queryForObject(getClubDetailSql,
                new Object[]{clubUuid.toString()},
                mapFitnessClubsFromDb());

        String getUserPostTimeSql = "select date(post_time) as date from fitness_club\n" +
                "         join exercise_post ep on fitness_club.club_uid = ep.location\n" +
                "where club_uid = ?::uuid and ep.username = ?" +
                "group by date\n" +
                "    order by date desc\n" +
                "limit 5";

        List<LocalDateTime> postTimes = jdbcTemplate.query(getUserPostTimeSql, new Object[]{clubUuid.toString(), username},
                ((resultSet, i) -> {

                    return resultSet.getTimestamp("date").toLocalDateTime();

                }));

        ((SimpleFitnessClubWithUserPost) result).setPostDateTimeList(postTimes);

        return Optional.ofNullable(result);
    }

    /**
     * coutry
     *
     * @param country
     * @return
     */
    @Override
    public List<FitnessClubSelectItem> getClubItemsByCountryCode(String country) {

        String sql = "select *, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club\n" +
                "join fitness_brand fb on fitness_club.club_brand = fb.id\n" +
                "join country c on fb.country = c.id\n" +
                "where alpha_two_code = ?";

        String allSql = "select *, fb.name as brand_name, c.name as country_name\n" +
                "from fitness_club\n" +
                "join fitness_brand fb on fitness_club.club_brand = fb.id\n" +
                "join country c on fb.country = c.id\n";

        if (country.toLowerCase().equals("all")) {
            return jdbcTemplate.query(
                    allSql,
                    new Object[]{country},
                    mapFitnessClubItemFromDb());
        } else {
            return jdbcTemplate.query(sql,
                    new Object[]{country},
                    mapFitnessClubItemFromDb());
        }

    }

    public RowMapper<FitnessClubSelectItem> mapFitnessClubItemFromDb() {
        return (resultSet, i) -> {
            String clubUuidStr = resultSet.getString("club_uid");
            UUID clubUuid = UUID.fromString(clubUuidStr);

            String name = resultSet.getString("club_name");

            String countryName = resultSet.getString("country_name");

            String brandName = resultSet.getString("brand_name");

            String city = resultSet.getString("city");

            String state = resultSet.getString("state");

            return new SimpleFitnessClubSelectItem(clubUuid, name, brandName, countryName, state, city);

        };
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

            int id = resultSet.getInt("club_id");
            String name = resultSet.getString("club_name");

            double latitude = resultSet.getDouble("latitude");
            double longitude = resultSet.getDouble("longitude");

            String homeUrl = resultSet.getString("club_home_url");
            String zipCode = resultSet.getString("zip_code");
            String address = resultSet.getString("address");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");

            int brandId = resultSet.getInt("brand_id");
            String brandName = resultSet.getString("brand_name");
            String icon = resultSet.getString("icon");

            int countryId = resultSet.getInt("country_id");
            String countryName = resultSet.getString("country_name");
            String alphaTwoCode = resultSet.getString("alpha_two_code");
            String alphaThreeCode = resultSet.getString("alpha_three_code");
            String region = resultSet.getString("region");
            String numericCode = resultSet.getString("numeric_code");
            String flagUrl = resultSet.getString("flag_url");

            Map<String, String> openHours = (Map<String, String>) resultSet.getObject("open_hours");

            return new SimpleFitnessClubWithBrandAndCountry(clubUid, name, id, latitude, longitude, address, city, state, zipCode, homeUrl, openHours, brandId, brandName, icon, countryId, countryName, alphaTwoCode, alphaThreeCode, region, numericCode, flagUrl);
        };
    }

    @Override
    public void save(FitnessClub fitnessClub) {

    }

    @Override
    public void saveAll(List<? extends FitnessClub> tList) {

    }

    @Override
    public void update(FitnessClub fitnessClub) {

    }

    @Override
    public void delete(UUID fitnessClubId) {

    }
}
