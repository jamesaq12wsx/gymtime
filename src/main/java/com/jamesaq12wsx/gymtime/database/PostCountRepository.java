package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PostCountRepository {

    List<PostCount> findAllByClub(UUID clubUuid, LocalDate date);

}
