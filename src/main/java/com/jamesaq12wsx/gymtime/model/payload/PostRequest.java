package com.jamesaq12wsx.gymtime.model.payload;

import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostRequest {
    /**
     * may be club uuid or null
     */
    private UUID clubUuid;

    private PostPrivacy privacy = PostPrivacy.PRIVATE;

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
