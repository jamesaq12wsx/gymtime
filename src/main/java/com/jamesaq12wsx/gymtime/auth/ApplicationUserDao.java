package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.database.Dao;

import java.util.Optional;

public interface ApplicationUserDao extends Dao<ApplicationUser> {

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);

    public boolean usernameExisted(String username);

}
