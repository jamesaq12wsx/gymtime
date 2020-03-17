package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mark {

    private UUID userUuid;

    private LocalDateTime dateTime;

    private UUID fitnessUuid;

}
