package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.FitnessClubDetail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FitnessClubDao extends Dao<FitnessClub> {

    List<FitnessClub> getClubsWithLatAndLon(double latitude, double longitude);

    boolean exist(UUID clubUuid);

}
