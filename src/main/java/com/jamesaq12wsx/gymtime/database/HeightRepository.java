package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.SimpleUserHeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeightRepository extends JpaRepository<SimpleUserHeight, Integer> {

    boolean existsByUser(ApplicationUser user);

    Optional<SimpleUserHeight> findByUser(ApplicationUser user);

    @Query(value = "select * from user_height where username = ?1", nativeQuery = true)
    Optional<SimpleUserHeight> findByUsername(String username);

}
