package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.FitnessClubDao;
import com.jamesaq12wsx.gymtime.database.FitnessClubDaoImpl;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.FitnessClubDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FitnessClubService {

    private final FitnessClubDao fitnessClubDao;

    @Autowired
    public FitnessClubService(FitnessClubDao fitnessClubDao) {
        this.fitnessClubDao = fitnessClubDao;
    }

    public List<FitnessClub> getAllFitnessClubs(){
        return fitnessClubDao.getAll();
    }

    public List<FitnessClub> getAllFitnessClubsWithLocation(double lat, double lon){
        return fitnessClubDao.getClubsWithLatAndLon(lat, lon);
    }

    public FitnessClub getFitnessDetail(UUID uuid){
        return fitnessClubDao.get(uuid).orElseThrow(() -> new ApiRequestException(String.format("Didn't found this club %s", uuid.toString())));
    }
}
