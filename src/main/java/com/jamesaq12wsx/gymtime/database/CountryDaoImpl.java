package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CountryDaoImpl implements CountryDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CountryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<SimpleCountry> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<SimpleCountry> getAll() {

        String sql = "select *\n" +
                " from country";

        return jdbcTemplate.query(sql, (resultSet, i) -> {

            int id = resultSet.getInt("id");

            String name = resultSet.getString("name");

            String alphaTwo = resultSet.getString("alpha_two_code");

            String alphaThree = resultSet.getString("alpha_three_code");

            String region = resultSet.getString("region");

            String numericCode = resultSet.getString("numeric_code");

            String flagUrl = resultSet.getString("flag_url");

            return new SimpleCountry(id, name, alphaTwo, alphaThree, region, numericCode, flagUrl);

        });

    }

    @Override
    public void save(SimpleCountry simpleCountry) {

    }

    @Override
    public void saveAll(List<? extends SimpleCountry> tList) {

    }

    @Override
    public void update(SimpleCountry simpleCountry) {

    }

    @Override
    public void delete(Integer countryId) {

    }
}
