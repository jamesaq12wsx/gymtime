package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Data;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
public class LogQueryCriteria {

    private String username;

    private String description;

    private String method;

    private String params;

    private String browser;

    private String requestIp;

    private String address;;

    private String logType;

    private Timestamp startTime;

    private Timestamp endTime;

}
