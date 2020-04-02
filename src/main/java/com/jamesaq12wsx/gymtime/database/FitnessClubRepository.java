package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleFitnessClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FitnessClubRepository extends JpaRepository<SimpleFitnessClub, UUID> {

    List<SimpleFitnessClub> findAllByBrand_Country_AlphaTwoCode(String countryCode);

    List<SimpleFitnessClub> findAllByBrand_BrandId(int brandId);

}
