package com.jamesaq12wsx.gymtime.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBodyStat {

    private SimpleUserHeight height;

    private List<SimpleUserWeight> weights;

    private List<SimpleUserBodyFat> bodyFats;

}
