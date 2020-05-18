package com.jamesaq12wsx.gymtime.service.impl;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.FitnessClubRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.service.UserDataService;
import com.jamesaq12wsx.gymtime.service.dto.UserShortDto;
import com.jamesaq12wsx.gymtime.service.mapper.UserShortMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final ApplicationUserRepository applicationUserRepository;
    private final FitnessClubRepository fitnessClubRepository;

    private final UserShortMapper userShortMapper;


    public UserDataServiceImpl(ApplicationUserRepository applicationUserRepository, FitnessClubRepository fitnessClubRepository, UserShortMapper userShortMapper) {
        this.applicationUserRepository = applicationUserRepository;
        this.fitnessClubRepository = fitnessClubRepository;
        this.userShortMapper = userShortMapper;
    }

    @Override
    public List<UserShortDto> getRecentUserByClubId(Long clubId, Principal principal) {

        if (!fitnessClubRepository.existsById(clubId)){
            throw new ApiRequestException(String.format("Club %s not existed", clubId));
        }

        return userShortMapper.toDto(applicationUserRepository.findRecentUserByClubId(clubId));

    }
}

