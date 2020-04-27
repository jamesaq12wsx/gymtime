package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.ExerciseRepository;
import com.jamesaq12wsx.gymtime.database.MuscleGroupRepository;
import com.jamesaq12wsx.gymtime.database.MuscleRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.SimpleMuscle;
import com.jamesaq12wsx.gymtime.model.SimpleMuscleGroup;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.Audit;
import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import com.jamesaq12wsx.gymtime.model.payload.ExerciseRequest;
import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
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

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, MuscleGroupRepository muscleGroupRepository, MuscleRepository muscleRepository, ApplicationUserRepository applicationUserRepository, ImageService imageService) {
        this.exerciseRepository = exerciseRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleRepository = muscleRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.imageService = imageService;
    }

    public List<? extends Exercise> getAllExercise(Principal principal){

        return exerciseRepository.findAllByUsername(principal.getName());
    }

    public Exercise getExerciseById(Integer id, Principal principal){

        return exerciseRepository.findByIdAndUsername(id, principal.getName()).orElseThrow(() -> new ApiRequestException(String.format("Exercise id %s not found", id)));

    }

    public Exercise addNewExercise(ExerciseRequest exerciseRequest, Principal principal){

        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ApiRequestException(String.format("User email %s could not find, cannot add new exercise", principal.getName())));

        List<String> imageUrls = imageService.saveImages(exerciseRequest.getImages());

        SimpleMuscleGroup muscleGroup = muscleGroupRepository.findById(exerciseRequest.getMuscleGroupId())
                .orElseThrow(() -> new ApiRequestException(String.format("Exercise muscle group %s not existed could not be null")));

        SimpleMuscle primaryMuscle = null;

        if (exerciseRequest.getPrimaryMuscleId() != null){
            primaryMuscle = muscleRepository.findById(exerciseRequest.getPrimaryMuscleId()).orElse(null);

        }

        SimpleMuscle secondaryMuscle = null;

        if (exerciseRequest.getSecondaryMuscleId() != null){
            secondaryMuscle = muscleRepository.findById(exerciseRequest.getSecondaryMuscleId()).orElse(null);

        }

        return exerciseRepository.save(new SimpleExercise(null, exerciseRequest.getName(), exerciseRequest.getDescription(), exerciseRequest.getMeasurementType(), muscleGroup, primaryMuscle, secondaryMuscle, imageUrls, false, user, new Audit()));

    }

    public Exercise updateExercise(Integer exerciseId, ExerciseRequest exerciseRequest, Principal principal){

        SimpleExercise updateExercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new ApiRequestException(String.format("Exercise id %s not found, couldn't update", exerciseId)));

        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ApiRequestException(String.format("User email %s could not find, cannot add new exercise", principal.getName())));

        if (user.getRole() != ApplicationUserRole.ADMIN &&updateExercise.getCreatedBy().getEmail() != principal.getName()){
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

        return exerciseRepository.save(updateExercise);

    }

    public void deleteExercise(Integer id, Principal principal){

        SimpleExercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new ApiRequestException(String.format("Cannot delete exercise, id %s not existed", id)));

        ApplicationUser user =
                applicationUserRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new ApiRequestException(String.format("Could not found user email %s, couldn't delete exercise", principal.getName())));

        if (exercise.getSystem()){
            if (user.getRole() != ApplicationUserRole.ADMIN){
                throw new ApiRequestException(String.format("User %s is not ADMIN, could not delete system exercise", user.getEmail()));
            }

        }else{
            if (exercise.getCreatedBy().getEmail() != principal.getName()){
                throw new ApiRequestException(String.format("User %s could not delete this exercise", user.getEmail()));
            }

        }

        exerciseRepository.deleteById(id);

    }

}
