package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ExerciseDao;
import com.jamesaq12wsx.gymtime.model.SimpleExerciseAudit;
import com.jamesaq12wsx.gymtime.model.payload.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ExerciseService {

    private final ExerciseDao exerciseDao;

    private final FileService fIleService;

    @Autowired
    public ExerciseService(ExerciseDao exerciseDao, FileService fIleService) {
        this.exerciseDao = exerciseDao;
        this.fIleService = fIleService;
    }

    public void addNewExercise(ExerciseRequest exerciseRequest, Principal principal){

        exerciseDao.save(new SimpleExerciseAudit(0, exerciseRequest.getName(), exerciseRequest.getDescription(), exerciseRequest.getCategory(), fIleService.saveImages(exerciseRequest.getImages()), principal.getName(), null, null));
    }

    public void updateExercise(ExerciseRequest exerciseRequest, Principal principal){

        exerciseDao.save(new SimpleExerciseAudit(exerciseRequest.getId(), exerciseRequest.getName(), exerciseRequest.getDescription(), exerciseRequest.getCategory(), fIleService.saveImages(exerciseRequest.getImages()), principal.getName(), null, null));

    }

}
