package com.jamesaq12wsx.gymtime.service.dto;

import com.jamesaq12wsx.gymtime.model.MeasurementType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class MeasurementUnitDto implements Serializable {

    private Integer id;

    private String type;

    private String name;

    private String alias;

}
