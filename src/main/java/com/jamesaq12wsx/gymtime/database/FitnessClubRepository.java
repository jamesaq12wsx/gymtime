package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.SimpleFitnessClub;
import net.bytebuddy.pool.TypePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FitnessClubRepository extends JpaRepository<SimpleFitnessClub, UUID> {

    List<SimpleFitnessClub> findAllByBrand_Country_AlphaTwoCode(String countryCode);

    List<SimpleFitnessClub> findAllByBrand_BrandId(int brandId);

    @Query(value =
            "select *, sqrt(pow(?1- latitude, 2)+ pow(?2 - longitude, 2)) as distance\n" +
            "from fitness_club\n" +
            "order by distance\n" +
            "limit 50;",nativeQuery = true)
    List<SimpleFitnessClub> findAllByLocation(Double lat, Double lng);

}
