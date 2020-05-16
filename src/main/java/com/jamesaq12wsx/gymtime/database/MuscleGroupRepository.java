package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Integer> {
}
