package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleMuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupRepository extends JpaRepository<SimpleMuscleGroup, Integer> {
}
