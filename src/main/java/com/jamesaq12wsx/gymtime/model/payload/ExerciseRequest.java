package com.jamesaq12wsx.gymtime.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExerciseRequest {

    private int id;

    private String name;

    private String description;

    private String category;

    private MultipartFile[] images;

}