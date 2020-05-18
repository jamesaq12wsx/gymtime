package com.jamesaq12wsx.gymtime.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "user_body_fat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBodyFat extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    @Column(name = "body_fat", nullable = false)
    private Double bodyFat;

    @Column(nullable = false)
    private LocalDate date;

}
