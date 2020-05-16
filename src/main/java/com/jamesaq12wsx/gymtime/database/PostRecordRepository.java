package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.PostRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRecordRepository extends JpaRepository<PostRecord, Long> {

    @Query(value = "select * from post_record where post_uuid = ?1", nativeQuery = true)
    List<PostRecord> findAllByPostUuid(Long postId);

}
