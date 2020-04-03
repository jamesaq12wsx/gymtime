package com.jamesaq12wsx.gymtime.model.payload;

import com.jamesaq12wsx.gymtime.model.ExerciseCategory;
import com.jamesaq12wsx.gymtime.model.SimpleExCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExerciseRequest {

    private int id;

    private String name;

    private String description;

    private Set<SimpleExCategory> category;

    private MultipartFile[] images;

}
