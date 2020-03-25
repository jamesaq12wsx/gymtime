package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExercisePostDetail extends ExercisePost {

    private String clubName;

    private String brandName;

    public ExercisePostDetail(UUID uuid, String username, LocalDateTime markTime, PostPrivacy privacy, UUID clubUuid, Map<String, String> exercises, String clubName, String brandName) {
        super(uuid, username, markTime, privacy, clubUuid, exercises);
        this.brandName = brandName;
        this.clubName = clubName;
    }
}
