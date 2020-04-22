package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.SimpleUserBodyFat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BodyFatRepository extends JpaRepository<SimpleUserBodyFat, Integer> {

    boolean existsByUserAndDate(ApplicationUser user, LocalDate date);

    @Query(value = "select * from user_body_fat where username = ?1", nativeQuery = true)
    List<SimpleUserBodyFat> findAllByUsername(String username);

}
