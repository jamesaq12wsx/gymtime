package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.SimpleExercisePostWithClubInfo;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.model.payload.UpdatePostRequest;
import com.jamesaq12wsx.gymtime.service.ExercisePostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
public class ExercisePostController {

    private final ExercisePostService exercisePostService;

    @Autowired
    public ExercisePostController(ExercisePostService exercisePostService) {
        this.exercisePostService = exercisePostService;
    }


    @GetMapping("/{year}")
    @PreAuthorize("hasAuthority('post:read')")
    public List<ExercisePost> getUserMarks(@PathVariable("year") String year, Principal principal){
        return exercisePostService.getAllPostByUserWithYear(year, principal);
    }

//    @GetMapping("/{yearMonth}")
//    @PreAuthorize("hasAuthority('post:read')")
//    public List<ExercisePost> getUserPostsByMonth(@PathVariable("yearMonth") LocalDate date, Principal principal){
//        return exercisePostService.getPostByUserMonthly;
//    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void exerciseMark(@RequestBody PostRequest post, Principal principal){
        exercisePostService.newPost(post, principal);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void updateExercisePost(@RequestBody UpdatePostRequest post, Principal principal){
        exercisePostService.update(post, principal);
    }

    @DeleteMapping("/{postUuid}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void deleteExercisePost(@PathVariable("postUuid") String postUuidStr, Principal principal){

        UUID postUuid = null;

        try{
            postUuid = UUID.fromString(postUuidStr);
        }catch (IllegalStateException e){
            e.printStackTrace();

            throw new ApiRequestException(String.format("Post uuid %s not valid", postUuidStr));

        }

        exercisePostService.delete(postUuid, principal);
    }

//    @PostMapping
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    public void quickPost(@RequestBody PostRequest post, Principal principal){
//        exercisePostService.newPost(post, principal);
//    }

}
