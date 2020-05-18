package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDto implements Serializable {

    private Long id;

    private LocalDateTime exerciseTime;

    private FitnessClubDto club;

    @JsonIgnoreProperties("post")
    private List<PostRecordDto> records;

}
