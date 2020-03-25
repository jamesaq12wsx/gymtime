package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.ExercisePostDetail;
import com.jamesaq12wsx.gymtime.model.payload.ClubPostHourCount;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.service.ExercisePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
    public List<ExercisePostDetail> getUserMarks(@PathVariable("year") String year, Principal principal){
        return exercisePostService.getAllPostByUserWithYear(year, principal);
    }

//    @GetMapping("/{yearMonth}")
//    @PreAuthorize("hasAuthority('post:read')")
//    public List<ExercisePost> getUserPostsByMonth(@PathVariable("yearMonth") LocalDate date, Principal principal){
//        return exercisePostService.getPostByUserMonthly;
//    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void exerciseMark(@RequestBody PostRequest mark, Principal principal){
        exercisePostService.newPost(mark, principal);
    }

//    @PostMapping
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    public void quickPost(@RequestBody PostRequest post, Principal principal){
//        exercisePostService.newPost(post, principal);
//    }

}
