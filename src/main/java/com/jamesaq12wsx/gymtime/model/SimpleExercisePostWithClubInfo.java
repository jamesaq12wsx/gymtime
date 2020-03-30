package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleExercisePostWithClubInfo extends SimpleExercisePost implements ExercisePost {

    private String clubName;

    private String brandName;

    public SimpleExercisePostWithClubInfo(UUID uuid, LocalDateTime postTime, PostPrivacy privacy, UUID clubUuid, List<PostExercise> exercises, String clubName, String brandName) {
        super(uuid, postTime, privacy, clubUuid, exercises);
        this.brandName = brandName;
        this.clubName = clubName;
    }
}
