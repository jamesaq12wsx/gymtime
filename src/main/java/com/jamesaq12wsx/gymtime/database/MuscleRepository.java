package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleMuscle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleRepository extends JpaRepository<SimpleMuscle, Integer> {
}
