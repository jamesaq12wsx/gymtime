package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.ExerciseMark;
import com.jamesaq12wsx.gymtime.model.payload.MarkRequest;
import com.jamesaq12wsx.gymtime.service.ExerciseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mark")
public class ExerciseMarkController {

    private final ExerciseMarkService exerciseMarkService;

    @Autowired
    public ExerciseMarkController(ExerciseMarkService exerciseMarkService) {
        this.exerciseMarkService = exerciseMarkService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('mark:read')")
    public List<ExerciseMark> getMarks(Principal principal){
        return exerciseMarkService.getAllMarks(principal);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void exerciseMark(@RequestBody MarkRequest mark, Principal principal){
        exerciseMarkService.newMark(mark, principal);
    }

}
