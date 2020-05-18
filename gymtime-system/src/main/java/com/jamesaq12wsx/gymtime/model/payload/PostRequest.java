package com.jamesaq12wsx.gymtime.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostRequest {
    /**
     * may be club uuid or null
     */
    @NotNull
    private Long clubId;

    @NotNull
    private LocalDateTime exerciseTime;
//    private List<SimplePostExercise> exercises;

//    public List<SimplePostExercise> getExercises() {
//
//        if (exercises == null){
//            return Collections.EMPTY_LIST;
//        }
//
//        return exercises;
//    }
}
