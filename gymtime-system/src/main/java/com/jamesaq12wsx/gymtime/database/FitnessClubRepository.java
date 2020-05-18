package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.FitnessClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FitnessClubRepository extends JpaRepository<FitnessClub, Long> {

    Optional<FitnessClub> findAllById(Long id);

    boolean existsById(Long id);

    List<FitnessClub> findAllByBrand_Country_AlphaTwoCode(String countryCode);

    List<FitnessClub> findAllByBrandId(int brandId);

    @Query(value =
            "select *, sqrt(pow(?1- latitude, 2)+ pow(?2 - longitude, 2)) as distance\n" +
            "from fitness_club\n" +
            "order by distance\n" +
            "limit 50;",nativeQuery = true)
    List<FitnessClub> findAllByLocation(Double lat, Double lng);

}
