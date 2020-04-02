package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<SimpleExercise, Integer> {

    @Query(value = "select * from exercise where created_by = 'system' or created_by = ?1", nativeQuery = true)
    List<SimpleExercise> findAllByAudit_CreatedBy(String username);

}
