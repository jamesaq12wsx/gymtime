package com.jamesaq12wsx.gymtime.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Entity
@Table(name = "sys_log")
@Getter
@Setter
@NoArgsConstructor
public class Log {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Log User */
    private String username;

    /** Description */
    private String description;

    /** Method Name */
    private String method;

    /** Parameters */
    private String params;

    /** Log Type */
    @Column(name = "log_type")
    private String logType;

    /** Request Ip */
    @Column(name = "request_ip")
    private String requestIp;

    /** Address */
    private String address;

    /** Browser  */
    private String browser;

    /** Request Time */
    private Long time;

    /** Exception Detail  */
    @Column(name = "exception_detail", columnDefinition = "bytea")
    private byte[] exceptionDetail;

    /** Created Time */
    @CreationTimestamp
    private Timestamp createdAt;

    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

}
