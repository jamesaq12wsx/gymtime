package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.User;
import com.jamesaq12wsx.gymtime.model.entity.UserHeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeightRepository extends JpaRepository<UserHeight, Integer> {

    @Query(value = "select * from user_height where username = ?1", nativeQuery = true)
    Optional<UserHeight> findByUsername(String username);

}
