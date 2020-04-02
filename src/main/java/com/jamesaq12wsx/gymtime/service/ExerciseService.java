package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.ExerciseRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.Audit;
import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import com.jamesaq12wsx.gymtime.model.payload.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ExerciseService {

//    private final ExerciseDao exerciseDao;

    private final ExerciseRepository exerciseRepository;

    private final ApplicationUserRepository applicationUserRepository;

    private final FileService fileService;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, ApplicationUserRepository applicationUserRepository, FileService fileService) {
        this.exerciseRepository = exerciseRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.fileService = fileService;
    }

    public List<? extends Exercise> getAllExercise(Principal principal){
        return exerciseRepository.findAllByAudit_CreatedBy(principal.getName());
    }

    public Exercise addNewExercise(ExerciseRequest exerciseRequest, Principal principal){

        return exerciseRepository.save(new SimpleExercise(0, exerciseRequest.getName(), exerciseRequest.getDescription(), exerciseRequest.getCategory(), fileService.saveImages(exerciseRequest.getImages()), new Audit(null, principal.getName(), null)));

    }

    public Exercise updateExercise(ExerciseRequest exerciseRequest, Principal principal){

        return exerciseRepository.save(new SimpleExercise(0, exerciseRequest.getName(), exerciseRequest.getDescription(), exerciseRequest.getCategory(), fileService.saveImages(exerciseRequest.getImages()), new Audit(null, principal.getName(), null)));

    }

    public void deleteExercise(Integer id){

        if (!exerciseRepository.existsById(id)){
            throw new ApiRequestException(String.format("Cannot delete exercise, id %s not existed", id));
        }

        exerciseRepository.deleteById(id);
    }

}
