package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class LogSmallDto implements Serializable {

    private String description;

    private String requestIp;

    private Long time;

    private String address;

    private String browser;

    private Timestamp createTime;

}
