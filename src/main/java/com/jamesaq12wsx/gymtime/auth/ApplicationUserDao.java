package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.database.Dao;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationUserDao extends Dao<ApplicationUser, UUID> {

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);

    public boolean usernameExisted(String username);

}
