package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.List;

@JsonSubTypes({@JsonSubTypes.Type(SimpleExercise.class)})
public interface Exercise {

    int getId();

    String getName();

    String getDescription();

    ExerciseCategory getCategory();

    List<String> getImages();

}
