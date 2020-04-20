package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<SimpleExercise, Integer> {

    @Query(value = "select * from exercise where system = true or created_by = ?1", nativeQuery = true)
    List<SimpleExercise> findAllByUsername(String username);

    @Query(value = "select * from exercise where id = ?1 and (system = true or created_by= ?2)", nativeQuery = true)
    Optional<SimpleExercise> findByIdAndUsername(Integer id, String username);

}
