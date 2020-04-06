package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.List;
import java.util.Set;

@JsonSubTypes({@JsonSubTypes.Type(SimpleExercise.class)})
public interface Exercise {

    int getId();

    String getName();

    String getDescription();

    Set<? extends ExCategory> getCategory();

    List<String> getImages();

}
