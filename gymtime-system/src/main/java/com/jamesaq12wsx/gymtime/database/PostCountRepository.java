package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.PostCount;

import java.time.LocalDate;
import java.util.List;

public interface PostCountRepository {

    List<PostCount> findAllByClub(Long clubUuid, LocalDate date);

}
