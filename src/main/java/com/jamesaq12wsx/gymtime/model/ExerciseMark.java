package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExerciseMark {

    private UUID uuid;

    private String username;

    private LocalDateTime markTime;

    /**
     * may be club uuid or null
     */
    private UUID clubUuid;

    private Map<String,String> exercises;

}
