package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.ImageIO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleImage implements Image {

    private int id;

    private String name;

}
