package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.SimpleExercisePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExercisePostRepository extends JpaRepository<SimpleExercisePost, UUID> {

    List<ExercisePost> findAllByAudit_CreatedBy(String username);

    @Query(value = "select * from exercise_post where created_by = ?1 and cast(extract(year from post_time) as text) = ?2", nativeQuery = true)
    List<ExercisePost> findAllByAudit_CreatedByAndYear(String username, String year);

}
