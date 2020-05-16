package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.BrandRepository;
import com.jamesaq12wsx.gymtime.database.CountryRepository;
import com.jamesaq12wsx.gymtime.database.FitnessClubRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.service.dto.FitnessClubDto;
import com.jamesaq12wsx.gymtime.service.mapper.FitnessClubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class FitnessClubService {

    private final FitnessClubRepository fitnessClubRepository;

    private final CountryRepository countryRepository;

    private final BrandRepository brandRepository;

    private final FitnessClubMapper fitnessClubMapper;

    @Autowired
    public FitnessClubService(FitnessClubRepository fitnessClubRepository, CountryRepository countryRepository, BrandRepository brandRepository, FitnessClubMapper fitnessClubMapper) {
        this.fitnessClubRepository = fitnessClubRepository;
        this.countryRepository = countryRepository;
        this.brandRepository = brandRepository;
        this.fitnessClubMapper = fitnessClubMapper;
    }

    public List<FitnessClubDto> getAllFitnessClubs(){

        return fitnessClubMapper.toDto(fitnessClubRepository.findAll());

    }

    public List<FitnessClubDto> getClubByLocation(Double lat, Double lng){

        if(lat == null || lng == null){
            throw new ApiRequestException(String.format("Location not valid, you should provide lat and lng"));
        }

        return fitnessClubMapper.toDto(fitnessClubRepository.findAllByLocation(lat, lng));
    }

    public List<FitnessClubDto> getAllClubsByBrandId(Integer brandId){

        if (!brandRepository.existsById(brandId)){
            throw new ApiRequestException(String.format("Brand id %s is not existed", brandId));
        }

        return fitnessClubMapper.toDto(fitnessClubRepository.findAllByBrandId(brandId));
    }

    public List<FitnessClubDto> getAllFitnessByCountry(String countryCode){

        if (!countryRepository.existsByAlphaTwoCode(countryCode)){
            throw new ApiRequestException(String.format("Country code %s is not valid", countryCode));
        }

        return fitnessClubMapper.toDto(fitnessClubRepository.findAllByBrand_Country_AlphaTwoCode(countryCode));
    }


    public FitnessClubDto getFitnessById(Long id, Principal principal) {

        return fitnessClubMapper.toDto(fitnessClubRepository.findAllById(id).orElseThrow(() -> new ApiRequestException(String.format("Cannot get club, club id %s is not existed", id))));

    }
}
