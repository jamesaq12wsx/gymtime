package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    @Query(value = "select * from exercise where system = true or created_by = ?1", nativeQuery = true)
    List<Exercise> findAllByUsername(String username);

    @Query(value = "select * from exercise where id = ?1 and (system = true or created_by= ?2)", nativeQuery = true)
    Optional<Exercise> findByIdAndUsername(Integer id, String username);

}
