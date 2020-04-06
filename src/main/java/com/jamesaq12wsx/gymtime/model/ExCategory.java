package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Set;

@JsonDeserialize(as = SimpleExCategory.class)
public interface ExCategory {

    int getCategoryId();

    String getCategoryName();

//    Set<? extends Exercise> getExercises();

}
