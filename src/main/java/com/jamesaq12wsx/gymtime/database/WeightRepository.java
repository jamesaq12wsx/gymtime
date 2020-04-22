package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.SimpleUserHeight;
import com.jamesaq12wsx.gymtime.model.entity.SimpleUserWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightRepository extends JpaRepository<SimpleUserWeight, Integer> {

    boolean existsByUserAndDate(ApplicationUser user, LocalDate date);

    @Query(value = "select * from user_weight where username = ?1", nativeQuery = true)
    List<SimpleUserWeight> findAllByUsername(String username);

}
