package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.payload.RecordRequest;

public class SimplePostRecordFactory {

    public static PostRecord getPostRecord(MeasurementUnit unit, RecordRequest request) {

        PostRecord newRecord = new PostRecord();

        newRecord.setMeasurementUnit(unit);

        switch (unit.getMeasurementType()){

            case WEIGHT:

                newRecord.setWeight(request.getWeight());

                newRecord.setReps(request.getReps());

                break;

            case DISTANCE:

                newRecord.setDistance(request.getDistance());

                newRecord.setMin(request.getMin());

                break;

            case DURATION:

                newRecord.setDuration(request.getDuration());

                newRecord.setReps(request.getReps());

                break;

            default:
                throw new ApiRequestException(String.format("Measurement unit %s not found", unit.getName()));
        }

        return newRecord;

    }

}
