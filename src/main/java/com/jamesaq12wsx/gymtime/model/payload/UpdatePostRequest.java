package com.jamesaq12wsx.gymtime.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jamesaq12wsx.gymtime.model.PostExercise;
import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import com.jamesaq12wsx.gymtime.model.SimplePostExercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatePostRequest {

    @NotNull
    private UUID uuid;

    private UUID clubUuid;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime postTime;

    private PostPrivacy privacy;

    private List<SimplePostExercise> exercises;

    public List<SimplePostExercise> getExercises() {

        if (exercises == null){
            return Collections.EMPTY_LIST;
        }

        return exercises;
    }

}
