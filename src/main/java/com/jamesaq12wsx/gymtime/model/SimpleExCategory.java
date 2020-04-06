package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exercise_category")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimpleExCategory implements ExCategory {

    @Id
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "name")
    private String categoryName;

//    @ManyToMany
//    @JoinTable(
//            name = "exercise_category_exercise",
//            joinColumns = @JoinColumn(name = "category_id"),
//            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
//    private Set<SimpleExercise> exercises;
}
