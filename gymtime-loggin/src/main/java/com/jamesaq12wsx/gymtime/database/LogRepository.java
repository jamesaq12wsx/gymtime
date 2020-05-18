package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author shaochinlin
 * @date 2020-05-16
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {

    /**
     * Delete Log By Log Type
     * @param logType log type
     */
    @Modifying
    @Query(value = "delete from sys_log where log_type = ?1", nativeQuery = true)
    void deleteByLogType(String logType);

}
