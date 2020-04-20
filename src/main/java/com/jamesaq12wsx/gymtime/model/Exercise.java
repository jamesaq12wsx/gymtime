package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.List;
import java.util.Set;

@JsonSubTypes({@JsonSubTypes.Type(SimpleExercise.class)})
public interface Exercise {

    Integer getId();

    String getName();

    String getDescription();

    MuscleGroup getMuscleGroup();

    Muscle getPrimaryMuscle();

    Muscle getSecondaryMuscle();

    List<String> getImages();

}
