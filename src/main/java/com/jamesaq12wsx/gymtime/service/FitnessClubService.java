package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.auth.ApplicationUserDao;
import com.jamesaq12wsx.gymtime.database.FitnessClubDao;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.FitnessClub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class FitnessClubService {

    private final FitnessClubDao fitnessClubDao;

    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public FitnessClubService(FitnessClubDao fitnessClubDao, ApplicationUserDao applicationUserDao) {
        this.fitnessClubDao = fitnessClubDao;
        this.applicationUserDao = applicationUserDao;
    }

    public List<FitnessClub> getAllFitnessClubs(){
        return fitnessClubDao.getAll();
    }

    public List<FitnessClub> getAllFitnessClubsWithLocation(double lat, double lon){
        return fitnessClubDao.getClubsWithLatAndLon(lat, lon);
    }

    public FitnessClub getFitnessDetail(UUID uuid, Principal principal) {

        if (!fitnessClubDao.exist(uuid)){
            throw new ApiRequestException(String.format("Club id %s is not existed", uuid));
        }

        if (principal == null || !applicationUserDao.usernameExisted(principal.getName())){
            return fitnessClubDao.get(uuid).orElseThrow(() -> new ApiRequestException(String.format("The club %s is not existed", uuid.toString())));
        }else{
            try {
                return fitnessClubDao.getClubWithUserPost(uuid, principal.getName()).orElseThrow(() -> new ApiRequestException(String.format("The club %s is not existed", uuid.toString())));
            } catch (SQLException e) {
                e.printStackTrace();

                throw new ApiRequestException(String.format("Sql statement error"));
            }
        }
    }
}
