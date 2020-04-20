package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementUnitRepository extends JpaRepository<SimpleMeasurementUnit, Integer> {
}
