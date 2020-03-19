package com.jamesaq12wsx.gymtime.model.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class MarkRequest {
    /**
     * may be club uuid or null
     */
    private UUID clubUuid;

    private Map<String,String> exercises;

}
