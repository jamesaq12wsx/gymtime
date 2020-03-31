package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.BrandDao;
import com.jamesaq12wsx.gymtime.database.BrandRepository;
import com.jamesaq12wsx.gymtime.database.CountryDao;
import com.jamesaq12wsx.gymtime.database.ExerciseDao;
import com.jamesaq12wsx.gymtime.model.Brand;
import com.jamesaq12wsx.gymtime.model.BrandWithCountry;
import com.jamesaq12wsx.gymtime.model.SimpleCountry;
import com.jamesaq12wsx.gymtime.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {

    private final CountryDao countryDao;

    private final ExerciseDao exerciseDao;

    private final BrandDao brandDao;

    private final BrandRepository brandRepository;

    @Autowired
    public InfoService(CountryDao countryDao, ExerciseDao exerciseDao, BrandDao brandDao, BrandRepository brandRepository) {
        this.countryDao = countryDao;
        this.exerciseDao = exerciseDao;
        this.brandDao = brandDao;
        this.brandRepository = brandRepository;
    }

    public List<SimpleCountry> getAllCountry(){
        return countryDao.getAll();
    }

    public List<Exercise> getAllExercise(){
        return exerciseDao.getAll();
    }

    public List<Exercise> getAllSimpleExercise(){
        return exerciseDao.getSimpleAll();
    }

    public List<? extends Brand> getAllBrand(){
        return brandRepository.findAll();
//        return brandDao.getAllWithCountry();
    }
}
