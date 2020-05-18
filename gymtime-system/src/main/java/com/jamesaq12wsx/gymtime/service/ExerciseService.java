package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.MuscleGroupRepository;
import com.jamesaq12wsx.gymtime.database.MuscleRepository;
import com.jamesaq12wsx.gymtime.model.entity.Muscle;
import com.jamesaq12wsx.gymtime.model.entity.MuscleGroup;
import com.jamesaq12wsx.gymtime.model.entity.User;
import com.jamesaq12wsx.gymtime.security.Role;
import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.ExerciseRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.entity.Exercise;
import com.jamesaq12wsx.gymtime.model.payload.ExerciseRequest;
import com.jamesaq12wsx.gymtime.service.dto.ExerciseDto;
import com.jamesaq12wsx.gymtime.service.mapper.ExerciseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ExerciseService {

//    private final ExerciseDao exerciseDao;

    private final ExerciseRepository exerciseRepository;

    private final MuscleGroupRepository muscleGroupRepository;

    private final MuscleRepository muscleRepository;

    private final ApplicationUserRepository applicationUserRepository;

    private final ImageService imageService;

    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, MuscleGroupRepository muscleGroupRepository, MuscleRepository muscleRepository, ApplicationUserRepository applicationUserRepository, ImageService imageService, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleRepository = muscleRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.imageService = imageService;
        this.exerciseMapper = exerciseMapper;
    }

    public List<? extends ExerciseDto> getAllExercise(Principal principal){

        return exerciseMapper.toDto(exerciseRepository.findAllByUsername(principal.getName()));
    }

    public ExerciseDto getExerciseById(Integer id, Principal principal){

        return exerciseMapper.toDto(exerciseRepository.findByIdAndUsername(id, principal.getName()).orElseThrow(() -> new ApiRequestException(String.format("Exercise id %s not found", id))));

    }

    public ExerciseDto addNewExercise(ExerciseRequest exerciseRequest, Principal principal){

        User user = applicationUserRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ApiRequestException(String.format("User email %s could not find, cannot add new exercise", principal.getName())));

        List<String> imageUrls = imageService.saveImages(exerciseRequest.getImages());

        MuscleGroup muscleGroup = muscleGroupRepository.findById(exerciseRequest.getMuscleGroupId())
                .orElseThrow(() -> new ApiRequestException(String.format("Exercise muscle group %s not existed could not be null")));

        Muscle primaryMuscle = null;

        if (exerciseRequest.getPrimaryMuscleId() != null){
            primaryMuscle = muscleRepository.findById(exerciseRequest.getPrimaryMuscleId()).orElse(null);

        }

        Muscle secondaryMuscle = null;

        if (exerciseRequest.getSecondaryMuscleId() != null){
            secondaryMuscle = muscleRepository.findById(exerciseRequest.getSecondaryMuscleId()).orElse(null);

        }

        Exercise newExercise = new Exercise(null, exerciseRequest.getName(), exerciseRequest.getDescription(), exerciseRequest.getMeasurementType(), muscleGroup, primaryMuscle, secondaryMuscle, imageUrls);

        newExercise.setCreatedBy(principal.getName());

        return exerciseMapper.toDto(exerciseRepository.save(newExercise));

    }

    public ExerciseDto updateExercise(Integer exerciseId, ExerciseRequest exerciseRequest, Principal principal){

        Exercise updateExercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new ApiRequestException(String.format("Exercise id %s not found, couldn't update", exerciseId)));

        User user = applicationUserRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ApiRequestException(String.format("User email %s could not find, cannot add new exercise", principal.getName())));

        if (user.getRole() != Role.ADMIN &&updateExercise.getCreatedBy() != principal.getName()){
            throw new ApiRequestException(String.format("User %s has no right update this exercise", user.getEmail()));
        }

        updateExercise.setName(exerciseRequest.getName());

        updateExercise.setDescription(exerciseRequest.getDescription());

        updateExercise.setMeasurementType(exerciseRequest.getMeasurementType());

        if (updateExercise.getMuscleGroup().getId() != exerciseRequest.getMuscleGroupId()){
            updateExercise.setMuscleGroup(muscleGroupRepository.findById(exerciseRequest.getMuscleGroupId())
                    .orElseThrow(() -> new ApiRequestException(String.format("Muscle group %s is not existed, you must set a muscle group", exerciseRequest.getMuscleGroupId()))));
        }

        if (updateExercise.getPrimaryMuscle().getId() != exerciseRequest.getPrimaryMuscleId()){
            updateExercise.setPrimaryMuscle(muscleRepository.findById(exerciseRequest.getPrimaryMuscleId()).orElse(null));
        }

        if (updateExercise.getSecondaryMuscle().getId() != exerciseRequest.getSecondaryMuscleId()){
            updateExercise.setSecondaryMuscle(muscleRepository.findById(exerciseRequest.getSecondaryMuscleId()).orElse(null));
        }

        return exerciseMapper.toDto(exerciseRepository.save(updateExercise));

    }

    public void deleteExercise(Integer id, Principal principal){

        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new ApiRequestException(String.format("Cannot delete exercise, id %s not existed", id)));

        User user =
                applicationUserRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new ApiRequestException(String.format("Could not found user email %s, couldn't delete exercise", principal.getName())));

        if (exercise.getCreatedBy().equals("system")){
            if (user.getRole() != Role.ADMIN){
                throw new ApiRequestException(String.format("User %s is not ADMIN, could not delete system exercise", user.getEmail()));
            }

        }else{
            if (!exercise.getCreatedBy().equals(principal.getName())){
                throw new ApiRequestException(String.format("User %s could not delete this exercise", user.getEmail()));
            }

        }

        exerciseRepository.deleteById(id);

    }

}
