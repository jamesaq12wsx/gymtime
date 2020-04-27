package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ExerciseRepository;
import com.jamesaq12wsx.gymtime.database.MeasurementUnitRepository;
import com.jamesaq12wsx.gymtime.database.PostRecordRepository;
import com.jamesaq12wsx.gymtime.database.PostRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import com.jamesaq12wsx.gymtime.model.entity.SimplePost;
import com.jamesaq12wsx.gymtime.model.entity.SimplePostRecord;
import com.jamesaq12wsx.gymtime.model.entity.SimplePostRecordFactory;
import com.jamesaq12wsx.gymtime.model.payload.RecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class PostRecordService {

    private final PostRecordRepository postRecordRepository;

    private final PostRepository postRepository;

    private final ExerciseRepository exerciseRepository;

    private final MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    public PostRecordService(PostRecordRepository postRecordRepository, PostRepository postRepository, ExerciseRepository exerciseRepository, MeasurementUnitRepository measurementUnitRepository) {
        this.postRecordRepository = postRecordRepository;
        this.postRepository = postRepository;
        this.exerciseRepository = exerciseRepository;
        this.measurementUnitRepository = measurementUnitRepository;
    }

    public List<SimplePostRecord> getByPostId(UUID postId){

        if (!postRepository.existsById(postId)){
            throw new ApiRequestException(String.format("Post id %s is not found, could not get records", postId));
        }

        return postRecordRepository.findAllByPostUuid(postId);
    }

    public SimplePostRecord getByRecordId(UUID recordId) throws Throwable {

        return postRecordRepository
                .findById(recordId)
                .orElseThrow(() -> new ApiRequestException(String.format("Record id %s not found, could not get record", recordId)));

    }

    public SimplePostRecord newRecord(UUID postId, RecordRequest request, Principal principal) {

        SimplePost post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiRequestException(String.format("Post id %s is not found, could not add record", postId)));

        if (!post.getUser().getEmail().equals(principal.getName())){
            throw new ApiRequestException(String.format("You cannot add record on this post %s", postId));
        }

        SimpleExercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new ApiRequestException(String.format("Exercise id %s not found, could not add record", request.getExerciseId())));

        SimpleMeasurementUnit measurementUnit = null;

        switch (exercise.getMeasurementType()){
            case WEIGHT:
                measurementUnit = post.getUser().getUserUnitSetting().getWeightUnit();
                break;
            case DISTANCE:
                measurementUnit = post.getUser().getUserUnitSetting().getDistanceUnit();
                break;
            default:
                throw new ApiRequestException(String.format("Exercise not support"));
        }

        SimplePostRecord newRecord = SimplePostRecordFactory.getPostRecord(measurementUnit, request);

        newRecord.setPost(post);

        newRecord.setExercise(exercise);

        return postRecordRepository.save(newRecord);
    }

    public SimplePostRecord updateRecord(UUID postId, UUID recordId, RecordRequest request, Principal principal){

        SimplePostRecord record = postRecordRepository.findById(recordId).orElseThrow(() -> new ApiRequestException("Record not found, could not delete"));

        if(!record.getPost().getUser().getEmail().equals(principal.getName())){
            throw new ApiRequestException(String.format("You could not update this record"));
        }

        if (record.getExercise().getId() != request.getExerciseId()){

            SimpleExercise exercise = exerciseRepository.findById(request.getExerciseId()).orElseThrow(() -> new ApiRequestException(""));

            record.setExercise(exercise);
        }

        SimpleMeasurementUnit measurementUnit = null;

        switch (record.getExercise().getMeasurementType()){
            case WEIGHT:
                measurementUnit = record.getPost().getUser().getUserUnitSetting().getWeightUnit();
                record.setMeasurementUnit(measurementUnit);
                break;
            case DISTANCE:
                measurementUnit = record.getPost().getUser().getUserUnitSetting().getDistanceUnit();
                record.setMeasurementUnit(measurementUnit);
                break;
            default:
                throw new ApiRequestException(String.format("Exercise not support unit not support"));
        }

        setRecord(record, request, record.getMeasurementUnit().getMeasurementType());

        return postRecordRepository.save(record);
    }

    private SimplePostRecord clearRecord(SimplePostRecord record){

        if (record == null){
            return record;
        }

        record.setReps(null);
        record.setWeight(null);
        record.setDistance(null);
        record.setDuration(null);
        record.setMin(null);

        return record;
    }

    private SimplePostRecord setRecord(SimplePostRecord record, RecordRequest request, MeasurementType type){

        switch (type){
            case WEIGHT:

                record.setWeight(request.getWeight());

                record.setReps(request.getReps());

                break;

            case DISTANCE:

                record.setDistance(request.getDistance());

                record.setMin(request.getMin());

                break;

            case DURATION:

                record.setDuration(request.getDuration());

                record.setReps(request.getReps());

                break;

            default:
                throw new ApiRequestException(String.format("Measurement type %s not found", type.getValue()));
        }

        return record;

    }

    public void deleteRecord(UUID recordId) throws Throwable {

        postRecordRepository.findById(recordId)
                .orElseThrow(() -> new ApiRequestException(String.format("Record id %s not found, could not delete it", recordId)));

        postRecordRepository.deleteById(recordId);

    }
}
