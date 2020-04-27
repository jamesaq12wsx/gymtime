package com.jamesaq12wsx.gymtime.model.payload;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecordRequest {

    private Integer exerciseId;

    private Double weight;

    private Integer reps;

    private Double distance;

    private Integer min;

    private Integer duration;

    private Map<String,Object> attributes;

    @JsonAnySetter
    void setAttributes(String key, Object value) {
        if (attributes == null){
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

}
