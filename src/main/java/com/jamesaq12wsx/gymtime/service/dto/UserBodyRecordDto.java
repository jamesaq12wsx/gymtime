package com.jamesaq12wsx.gymtime.service.dto;

import com.jamesaq12wsx.gymtime.model.entity.UserBodyFat;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyRecord;
import com.jamesaq12wsx.gymtime.model.entity.UserHeight;
import com.jamesaq12wsx.gymtime.model.entity.UserWeight;
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
