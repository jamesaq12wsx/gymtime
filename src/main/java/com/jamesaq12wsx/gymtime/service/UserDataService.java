package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.model.UserData;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserDataService {

    List<? extends UserData> getRecentUserByClubId(UUID clubId, Principal principal);

}
