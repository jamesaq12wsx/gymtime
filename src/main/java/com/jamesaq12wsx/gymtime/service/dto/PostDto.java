package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.entity.FitnessClub;
import com.jamesaq12wsx.gymtime.model.entity.PostRecord;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostDto implements Serializable {

    private Long id;

    private LocalDateTime exerciseTime;

    private FitnessClubDto club;

    @JsonIgnoreProperties("post")
    private List<PostRecordDto> records;

}
