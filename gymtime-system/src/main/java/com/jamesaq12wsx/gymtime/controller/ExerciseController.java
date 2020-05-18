package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.service.ExerciseService;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import com.jamesaq12wsx.gymtime.model.payload.ExerciseRequest;
import com.jamesaq12wsx.gymtime.service.dto.ExerciseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    private final ApiResponseBuilder apiResponseBuilder;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ApiResponseBuilder apiResponseBuilder) {
        this.exerciseService = exerciseService;
        this.apiResponseBuilder = apiResponseBuilder;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('exercise:read')")
    public ApiResponse<List<? extends ExerciseDto>> getAllExercise(Principal principal){

        List<? extends ExerciseDto> results = exerciseService.getAllExercise(principal);

        return apiResponseBuilder.createSuccessResponse(results);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('exercise:read')")
    public ApiResponse<ExerciseDto> getExerciseById(@PathVariable("id") Integer id, Principal principal){

        ExerciseDto ex = exerciseService.getExerciseById(id, principal);

        return apiResponseBuilder.createSuccessResponse(ex);

    }

    @PreAuthorize("hasAuthority('exercise:write')")
    @PostMapping
    public ApiResponse<ExerciseDto> newExercise(@RequestBody ExerciseRequest request, Principal principal){

        ExerciseDto newExercise = exerciseService.addNewExercise(request, principal);

        return apiResponseBuilder.createSuccessResponse(newExercise);

    }

    @PreAuthorize("hasAuthority('exercise:write')")
    @PutMapping("/{id}")
    public ApiResponse<ExerciseDto> updateExercise(@RequestParam("id") int id, @RequestBody ExerciseRequest request, Principal principal){

        ExerciseDto updatedExercise = exerciseService.updateExercise(id, request, principal);

        return apiResponseBuilder.createSuccessResponse(updatedExercise);

    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteExercise(@PathVariable("id") Integer id, Principal principal){

        exerciseService.deleteExercise(id, principal);

        return apiResponseBuilder.createSuccessResponse("delete success");
    }



}
