package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.auth.ApplicationUserDao;
import com.jamesaq12wsx.gymtime.database.ExerciseMarkDao;
import com.jamesaq12wsx.gymtime.database.ExerciseMarkDaoImpl;
import com.jamesaq12wsx.gymtime.database.FitnessClubDao;
import com.jamesaq12wsx.gymtime.database.FitnessClubDaoImpl;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ExerciseMark;
import com.jamesaq12wsx.gymtime.model.payload.MarkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExerciseMarkService {

    private final FitnessClubDao fitnessClubDao;

    private final ApplicationUserDao applicationUserDao;

    private final ExerciseMarkDao exerciseMarkDao;

    @Autowired
    public ExerciseMarkService(FitnessClubDaoImpl fitnessClubDao, ApplicationUserDao applicationUserDao, ExerciseMarkDaoImpl exerciseMarkDao) {
        this.fitnessClubDao = fitnessClubDao;
        this.applicationUserDao = applicationUserDao;
        this.exerciseMarkDao = exerciseMarkDao;
    }

    public List<ExerciseMark> getAllMarks(Principal principal){
        return exerciseMarkDao.getAllMarksByUser(principal.getName());
    }

    public void newMark(MarkRequest mark, Principal principal){

        exerciseMarkDao.save(new ExerciseMark(null, principal.getName(), LocalDateTime.now(), mark.getClubUuid(), mark.getExercises()));
    }

}
