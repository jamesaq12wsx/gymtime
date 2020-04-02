package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.PostCount;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExercisePostDao extends Dao<ExercisePost, UUID> {

    List<ExercisePost> getAllMarksByUser(String username);

    List<ExercisePost> getAllPostsByUserWithYear(String year, String username);

    List<PostCount> getGymHourPost(UUID clubUuid, LocalDate date);

}
