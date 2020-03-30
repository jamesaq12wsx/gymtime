package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.CountryDao;
import com.jamesaq12wsx.gymtime.database.ExerciseDao;
import com.jamesaq12wsx.gymtime.model.Country;
import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.ExerciseAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {

    private final CountryDao countryDao;

    private final ExerciseDao exerciseDao;

    @Autowired
    public InfoService(CountryDao countryDao, ExerciseDao exerciseDao) {
        this.countryDao = countryDao;
        this.exerciseDao = exerciseDao;
    }

    public List<Country> getAllCountry(){
        return countryDao.getAll();
    }

    public List<Exercise> getAllExercise(){
        return exerciseDao.getAll();
    }

    public List<Exercise> getAllSimpleExercise(){
        return exerciseDao.getSimpleAll();
    }
}
