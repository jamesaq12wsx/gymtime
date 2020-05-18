package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface ApplicationUserRepositoryCustom {

    List<User> findRecentUserByClubId(Long clubId);

    List<User> findRecentUserByClubIdExcludeUser(UUID clubId, String username);

}
