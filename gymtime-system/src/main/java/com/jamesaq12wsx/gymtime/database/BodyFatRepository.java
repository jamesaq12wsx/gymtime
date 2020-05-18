package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.User;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyFat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BodyFatRepository extends JpaRepository<UserBodyFat, Integer> {

    boolean existsByUserAndDate(User user, LocalDate date);

    @Query(value = "select * from user_body_fat where username = ?1", nativeQuery = true)
    List<UserBodyFat> findAllByUsername(String username);

}
