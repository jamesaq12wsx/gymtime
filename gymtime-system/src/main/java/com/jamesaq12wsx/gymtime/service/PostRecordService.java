package com.jamesaq12wsx.gymtime.service;

import com.gymtime.database.*;
import com.gymtime.model.entity.*;
import com.jamesaq12wsx.gymtime.database.*;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.jamesaq12wsx.gymtime.model.entity.*;
import com.jamesaq12wsx.gymtime.model.payload.RecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class PostRecordService {

    private final ApplicationUserRepository userRepository;

    private final PostRecordRepository postRecordRepository;

    private final PostRepository postRepository;

    private final ExerciseRepository exerciseRepository;

    private final MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    public PostRecordService(ApplicationUserRepository userRepository, PostRecordRepository postRecordRepository, PostRepository postRepository, ExerciseRepository exerciseRepository, MeasurementUnitRepository measurementUnitRepository) {
        this.userRepository = userRepository;
        this.postRecordRepository = postRecordRepository;
        this.postRepository = postRepository;
        this.exerciseRepository = exerciseRepository;
        this.measurementUnitRepository = measurementUnitRepository;
    }

    public List<PostRecord> getByPostId(Long postId){

        if (!postRepository.existsById(postId)){
            throw new ApiRequestException(String.format("Post id %s is not found, could not get records", postId));
        }

        return postRecordRepository.findAllByPostUuid(postId);
    }

    public PostRecord getByRecordId(Long recordId) throws Throwable {

        return postRecordRepository
                .findById(recordId)
                .orElseThrow(() -> new ApiRequestException(String.format("Record id %s not found, could not get record", recordId)));

    }

    public PostRecord newRecord(Long postId, RecordRequest request, Principal principal) {

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ApiRequestException(String.format("User %s not existed", principal.getName())));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiRequestException(String.format("Post id %s is not found, could not add record", postId)));

        if (!post.getCreatedBy().equals(principal.getName())){
            throw new ApiRequestException(String.format("You cannot add record on this post %s", postId));
        }

        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new ApiRequestException(String.format("Exercise id %s not found, could not add record", request.getExerciseId())));

        MeasurementUnit measurementUnit = null;

        switch (exercise.getMeasurementType()){
            case WEIGHT:
                measurementUnit = user.getUserUnitSetting().getWeightUnit();
                break;
            case DISTANCE:
                measurementUnit = user.getUserUnitSetting().getDistanceUnit();
                break;
            default:
                throw new ApiRequestException(String.format("Exercise not support"));
        }

        PostRecord newRecord = SimplePostRecordFactory.getPostRecord(measurementUnit, request);

        newRecord.setPost(post);

        newRecord.setExercise(exercise);

        return postRecordRepository.save(newRecord);
    }

    public PostRecord updateRecord(Long postId, Long recordId, RecordRequest request, Principal principal){

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ApiRequestException(String.format("User %s not existed", principal)));

        PostRecord record = postRecordRepository.findById(recordId).orElseThrow(() -> new ApiRequestException("Record not found, could not delete"));

        if(!user.getEmail().equals(principal.getName())){
            throw new ApiRequestException(String.format("You could not update this record"));
        }

        if (record.getExercise().getId() != request.getExerciseId()){

            Exercise exercise = exerciseRepository.findById(request.getExerciseId()).orElseThrow(() -> new ApiRequestException(""));

            record.setExercise(exercise);
        }

        MeasurementUnit measurementUnit = null;

        switch (record.getExercise().getMeasurementType()){
            case WEIGHT:
                measurementUnit = user.getUserUnitSetting().getWeightUnit();
                record.setMeasurementUnit(measurementUnit);
                break;
            case DISTANCE:
                measurementUnit = user.getUserUnitSetting().getDistanceUnit();
                record.setMeasurementUnit(measurementUnit);
                break;
            default:
                throw new ApiRequestException(String.format("Exercise not support unit not support"));
        }

        setRecord(record, request, record.getMeasurementUnit().getMeasurementType());

        return postRecordRepository.save(record);
    }

    private PostRecord clearRecord(PostRecord record){

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

    private PostRecord setRecord(PostRecord record, RecordRequest request, MeasurementType type){

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

    public void deleteRecord(Long recordId) throws Throwable {

        postRecordRepository.findById(recordId)
                .orElseThrow(() -> new ApiRequestException(String.format("Record id %s not found, could not delete it", recordId)));

        postRecordRepository.deleteById(recordId);

    }
}
