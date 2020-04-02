package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.*;
import com.jamesaq12wsx.gymtime.model.Brand;
import com.jamesaq12wsx.gymtime.model.SimpleCountry;
import com.jamesaq12wsx.gymtime.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {

    private final CountryRepository countryRepository;

//    private final ExerciseDao exerciseDao;

    private final ExerciseRepository exerciseRepository;

    private final BrandRepository brandRepository;

    @Autowired
    public InfoService(CountryRepository countryRepository, ExerciseRepository exerciseRepository, BrandRepository brandRepository) {
        this.countryRepository = countryRepository;
        this.exerciseRepository = exerciseRepository;
        this.brandRepository = brandRepository;
    }

    public List<SimpleCountry> getAllCountry(){
//        return countryDao.getAll();
        return countryRepository.findAll();
    }

    public List<? extends Exercise> getAllExercise(){
        return exerciseRepository.findAll();
    }

    public List<? extends Exercise> getAllSimpleExercise(){
        return exerciseRepository.findAll();
    }

    public List<? extends Brand> getAllBrand(){
        return brandRepository.findAll();
//        return brandDao.getAllWithCountry();
    }
}
