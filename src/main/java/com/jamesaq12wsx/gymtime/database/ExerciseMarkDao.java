package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.ExerciseMark;

import java.util.List;
import java.util.UUID;

public interface ExerciseMarkDao extends Dao<ExerciseMark> {

    List<ExerciseMark> getAllMarksByUser(String username);

}
