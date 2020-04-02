package com.jamesaq12wsx.gymtime.model.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jamesaq12wsx.gymtime.model.PostExercise;
import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import com.jamesaq12wsx.gymtime.model.SimplePostExercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    private PostPrivacy privacy;

    private List<SimplePostExercise> exercises;

    public List<SimplePostExercise> getExercises() {

        if (exercises == null){
            return Collections.EMPTY_LIST;
        }

        return exercises;
    }
}
