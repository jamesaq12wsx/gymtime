package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.User;
import com.jamesaq12wsx.gymtime.model.entity.UserWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WeightRepository extends JpaRepository<UserWeight, Integer> {

    boolean existsByUserAndDate(User user, LocalDate date);

    @Query(value = "select * from user_weight where username = ?1", nativeQuery = true)
    List<UserWeight> findAllByUsername(String username);

}
