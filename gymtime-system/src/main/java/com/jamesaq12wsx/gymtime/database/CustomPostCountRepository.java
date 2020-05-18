package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.PostCount;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CustomPostCountRepository {

    public List<PostCount> findAllByClub(UUID clubUuid, LocalDate date);

}
