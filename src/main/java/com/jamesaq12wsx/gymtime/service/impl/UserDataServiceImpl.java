package com.jamesaq12wsx.gymtime.service.impl;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.FitnessClubRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.UserData;
import com.jamesaq12wsx.gymtime.service.UserDataService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final ApplicationUserRepository applicationUserRepository;
    private final FitnessClubRepository fitnessClubRepository;


    public UserDataServiceImpl(ApplicationUserRepository applicationUserRepository, FitnessClubRepository fitnessClubRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.fitnessClubRepository = fitnessClubRepository;
    }

    @Override
    public List<? extends UserData> getRecentUserByClubId(UUID clubId, Principal principal) {

        if (!fitnessClubRepository.existsById(clubId)){
            throw new ApiRequestException(String.format("Club %s not existed", clubId));
        }

        if (principal == null){
            return applicationUserRepository.findRecentUserByClubId(clubId);
        }else{
            return applicationUserRepository.findRecentUserByClubIdExcludeUser(clubId, principal.getName());
        }

    }
}

