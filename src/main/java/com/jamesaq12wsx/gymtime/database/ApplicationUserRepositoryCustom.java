package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.UserData;

import java.util.List;
import java.util.UUID;

public interface ApplicationUserRepositoryCustom {

    List<? extends UserData> findRecentUserByClubId(UUID clubId);

    List<? extends UserData> findRecentUserByClubIdExcludeUser(UUID clubId, String username);

}
