package com.jamesaq12wsx.gymtime.model.payload;

import com.jamesaq12wsx.gymtime.model.MeasurementType;

import javax.persistence.Enumerated;
import javax.persistence.Id;

public class SimpleMeasurementType {

    @Id
    private int id;

    @Enumerated
    private MeasurementType name;

}
