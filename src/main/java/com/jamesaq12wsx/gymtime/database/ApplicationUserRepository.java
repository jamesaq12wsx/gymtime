package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.auth.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    Optional<ApplicationUser> findByUsername(String username);

    boolean existsByUsername(String username);

}