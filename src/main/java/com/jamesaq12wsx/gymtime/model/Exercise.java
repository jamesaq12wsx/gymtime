package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.time.LocalDateTime;
import java.util.List;

@JsonSubTypes({@JsonSubTypes.Type(SimpleExercise.class), @JsonSubTypes.Type(SimpleExerciseAudit.class)})
public interface Exercise {

    int getId();

    String getName();

    String getDescription();

    String getCategory();

    List<String> getImages();

}
