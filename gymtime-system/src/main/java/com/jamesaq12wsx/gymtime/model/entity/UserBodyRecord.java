package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBodyRecord extends BaseEntity implements Serializable {

    private UserHeight height;

    private List<UserWeight> weights;

    private List<UserBodyFat> bodyFats;

}
