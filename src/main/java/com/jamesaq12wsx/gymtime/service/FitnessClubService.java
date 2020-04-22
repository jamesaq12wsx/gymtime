package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.BrandRepository;
import com.jamesaq12wsx.gymtime.database.CountryRepository;
import com.jamesaq12wsx.gymtime.database.FitnessClubRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.FitnessClubSelectItem;
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

    @Autowired
    public FitnessClubService(FitnessClubRepository fitnessClubRepository, CountryRepository countryRepository, BrandRepository brandRepository) {
        this.fitnessClubRepository = fitnessClubRepository;
//        this.applicationUserDao = applicationUserDao;
        this.countryRepository = countryRepository;
        this.brandRepository = brandRepository;
    }

    public List<? extends FitnessClub> getAllFitnessClubs(){

        return fitnessClubRepository.findAll();

    }

    public List<? extends FitnessClub> getClubByLocation(Double lat, Double lng){

        if(lat == null || lng == null){
            throw new ApiRequestException(String.format("Location not valid, you should provide lat and lng"));
        }

        return fitnessClubRepository.findAllByLocation(lat, lng);
    }

    public List<? extends FitnessClub> getAllClubsByBrandId(Integer brandId){

        if (!brandRepository.existsById(brandId)){
            throw new ApiRequestException(String.format("Brand id %s is not existed", brandId));
        }

        return fitnessClubRepository.findAllByBrand_BrandId(brandId);
    }

    public List<? extends FitnessClub> getAllFitnessByCountry(String countryCode){

        if (!countryRepository.existsByAlphaTwoCode(countryCode)){
            throw new ApiRequestException(String.format("Country code %s is not valid", countryCode));
        }

        return fitnessClubRepository.findAllByBrand_Country_AlphaTwoCode(countryCode);
    }

    /**
     *
     * @param country alpha two code
     * @return
     */
    public List<FitnessClubSelectItem> getFitnessSelectItems(String country){

        return null;

//        return fitnessClubDao.getClubItemsByCountryCode(country);

    }

    public FitnessClub getFitnessById(UUID uuid, Principal principal) {

        return fitnessClubRepository.findById(uuid).orElseThrow(() -> new ApiRequestException(String.format("Cannot get club, club id %s is not existed", uuid)));

    }
}
