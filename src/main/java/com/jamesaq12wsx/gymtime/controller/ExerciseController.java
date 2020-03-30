package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.payload.ExerciseRequest;
import com.jamesaq12wsx.gymtime.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PreAuthorize("hasAuthority('exercise:write')")
    @PostMapping
    public void newExercise(ExerciseRequest request, Principal principal){
        exerciseService.addNewExercise(request, principal);
    }

    @PreAuthorize("hasAuthority('exercise:write')")
    @PutMapping("/{id}")
    public void updateExercise(@RequestParam("id") int id, @RequestBody ExerciseRequest request, Principal principal){

        request.setId(id);

        exerciseService.updateExercise(request, principal);

    }

}
