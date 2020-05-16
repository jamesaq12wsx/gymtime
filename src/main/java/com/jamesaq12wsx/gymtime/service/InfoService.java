package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.*;
import com.jamesaq12wsx.gymtime.service.dto.BrandDto;
import com.jamesaq12wsx.gymtime.service.dto.CountryDto;
import com.jamesaq12wsx.gymtime.service.dto.ExerciseDto;
import com.jamesaq12wsx.gymtime.service.mapper.BrandMapper;
import com.jamesaq12wsx.gymtime.service.mapper.CountryMapper;
import com.jamesaq12wsx.gymtime.service.mapper.ExerciseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {

    private final CountryRepository countryRepository;

    private final ExerciseRepository exerciseRepository;

    private final BrandRepository brandRepository;

    private final ExerciseMapper exerciseMapper;

    private final BrandMapper brandMapper;

    private final CountryMapper countryMapper;

    @Autowired
    public InfoService(CountryRepository countryRepository, ExerciseRepository exerciseRepository, BrandRepository brandRepository, ExerciseMapper exerciseMapper, BrandMapper brandMapper, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.exerciseRepository = exerciseRepository;
        this.brandRepository = brandRepository;
        this.exerciseMapper = exerciseMapper;
        this.brandMapper = brandMapper;
        this.countryMapper = countryMapper;
    }

    public List<CountryDto> getAllCountry(){
//        return countryDao.getAll();
        return countryMapper.toDto(countryRepository.findAll());
    }

    public List<ExerciseDto> getAllExercise(){

        return exerciseMapper.toDto(exerciseRepository.findAll());
    }

    public List<ExerciseDto> getAllSimpleExercise(){
        return exerciseMapper.toDto(exerciseRepository.findAll());
    }

    public List<BrandDto> getAllBrand(){
        return brandMapper.toDto(brandRepository.findAll());
//        return brandDao.getAllWithCountry();
    }
}
