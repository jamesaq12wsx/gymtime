package com.jamesaq12wsx.gymtime.model.payload;

import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class PostRequest {
    /**
     * may be club uuid or null
     */
    @NotNull
    private UUID clubUuid;

    private PostPrivacy privacy;

    private Map<String,String> exercises;

    public Map<String, String> getExercises() {

        if (exercises == null){
            return Collections.EMPTY_MAP;
        }

        return exercises;
    }
}
