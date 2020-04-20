package com.jamesaq12wsx.gymtime.model.payload;

import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatePostRequest {

    @NotNull
    private UUID postUuid;

    private UUID clubUuid;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime exerciseTime;

    private PostPrivacy privacy;

}
