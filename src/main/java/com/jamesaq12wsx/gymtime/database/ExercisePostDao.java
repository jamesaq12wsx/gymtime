package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.Post;
import com.jamesaq12wsx.gymtime.model.PostCount;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExercisePostDao extends Dao<Post, UUID> {

    List<Post> getAllMarksByUser(String username);

    List<Post> getAllPostsByUserWithYear(String year, String username);

    List<PostCount> getGymHourPost(UUID clubUuid, LocalDate date);

}
