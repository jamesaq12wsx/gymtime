package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<SimpleCountry, Integer> {

    boolean existsByAlphaTwoCode(String countryCode);

}
