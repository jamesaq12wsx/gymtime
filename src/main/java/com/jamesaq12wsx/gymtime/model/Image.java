package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SimpleImage.class)
public interface Image {

    int getId();

    String getName();

}
