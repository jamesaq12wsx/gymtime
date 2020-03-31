package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BrandDaoImpl implements BrandDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BrandDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Brand> get(UUID id) {



        return Optional.empty();
    }

    @Override
    public List<Brand> getAll() {

        String query = "select * from fitness_brand";

        return jdbcTemplate.query(query, brandRowMapper());

    }

    @Override
    public List<BrandWithCountry> getAllWithCountry() {

        String query = "select *, fb.id brand_id, fb.name brand_name, c.id country_id, c.name country_name\n" +
                "from fitness_brand fb\n" +
                "join country c on fb.country = c.id;";

        return jdbcTemplate.query(
                query,
                brandWithCountryRowMapper());

    }

    @Override
    public void save(Brand brand) {

        SimpleBrandAudit brandAudit = (SimpleBrandAudit) brand;

        String insertSql = "insert into fitness_brand(name, country, icon, created_by)\n" +
                "values(?,?,?,?)";

        jdbcTemplate.update(
                insertSql,
                new Object[]{brandAudit.getBrandName(), brandAudit.getCountryId(), brandAudit.getIcon(), brandAudit.getCreatedBy()});

    }

    @Override
    public void saveAll(List<? extends Brand> tList) {

    }

    @Override
    public void update(Brand brandAudit) {

        String updateSql = "update fitness_brand\n" +
                "set name = ?,\n" +
                "country = ?, \n" +
                "icon = ? \n" +
                "where id = ?";

        jdbcTemplate.update(
                updateSql,
                new Object[]{brandAudit.getBrandName(), brandAudit.getCountryId(), brandAudit.getIcon(), brandAudit.getBrandId()});

    }

    @Override
    public void delete(Integer id) {
        String deleteSql = "delete from fitness_brand\n" +
                "where id = ?";

        jdbcTemplate.update(
                deleteSql,
                new Object[]{id}
        );
    }

    private RowMapper<Brand> brandRowMapper(){
        return ((resultSet, i) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int countryId = resultSet.getInt("country");
            String icon = resultSet.getString("icon");

            return new SimpleBrand(id, name, countryId, icon);

        });
    }

    private RowMapper<BrandWithCountry> brandWithCountryRowMapper(){
        return ((resultSet, i) -> {
            int id = resultSet.getInt("brand_id");
            String name = resultSet.getString("brand_name");
            int countryId = resultSet.getInt("country_id");
            String countryName = resultSet.getString("country_name");
            String icon = resultSet.getString("icon");
            String alphaTwo = resultSet.getString("alpha_two_code");
            String alphaThree = resultSet.getString("alpha_three_code");
            String region = resultSet.getString("region");
            String numericCode = resultSet.getString("numeric_code");
            String flagUrl = resultSet.getString("flag_url");

            return new SimpleBrandWithCountry(id, name, countryId, icon, countryName, alphaTwo, alphaThree, region, numericCode, flagUrl);

        });
    }

}
