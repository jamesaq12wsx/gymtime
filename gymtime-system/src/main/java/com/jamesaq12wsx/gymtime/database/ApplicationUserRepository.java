package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<User, Long>, ApplicationUserRepositoryCustom {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
