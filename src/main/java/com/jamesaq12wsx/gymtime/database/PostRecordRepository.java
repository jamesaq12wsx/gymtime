package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.SimplePostRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

public interface PostRecordRepository extends JpaRepository<SimplePostRecord, UUID> {

    @Query(value = "select * from post_record where post_uuid = ?1", nativeQuery = true)
    List<SimplePostRecord> findAllByPostUuid(UUID postId);

}
