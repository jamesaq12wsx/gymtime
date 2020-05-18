package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserBodyRecordDto implements Serializable {

    private UserHeightDto height;

    private List<UserWeightDto> weights;

    private List<UserBodyFatDto> bodyFats;

}
