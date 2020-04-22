package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.Post;
import com.jamesaq12wsx.gymtime.model.entity.SimplePostRecord;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.model.payload.RecordRequest;
import com.jamesaq12wsx.gymtime.model.payload.UpdatePostRequest;
import com.jamesaq12wsx.gymtime.service.PostRecordService;
import com.jamesaq12wsx.gymtime.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    private final PostRecordService postRecordService;

    private final ApiResponseBuilder apiResponseBuilder;

    @Autowired
    public PostController(PostService postService, PostRecordService postRecordService, ApiResponseBuilder apiResponseBuilder) {
        this.postService = postService;
        this.postRecordService = postRecordService;
        this.apiResponseBuilder = apiResponseBuilder;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<List<? extends Post>> getAllUserPost(Principal principal){

        List<? extends Post> results = postService.getAllPostByUser(principal);

        return apiResponseBuilder.createSuccessResponse(results);
    }

    @GetMapping("/{postUuid}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<Post> getUserPostById(@PathVariable("postUuid")UUID postId, Principal principal){

        return apiResponseBuilder.createSuccessResponse(postService.getPostById(postId, principal));

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<Post> newPost(@RequestBody PostRequest post, Principal principal){
        return ApiResponseBuilder.createSuccessResponse(postService.newPost(post, principal));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<Post> updateExercisePost(@RequestBody UpdatePostRequest post, Principal principal){
        return ApiResponseBuilder.createSuccessResponse(postService.update(post, principal));
    }

    @DeleteMapping("/{postUuid}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse deleteExercisePost(@PathVariable("postUuid") UUID postUuid, Principal principal){

        postService.delete(postUuid, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @GetMapping("/{postUuid}/record")
    public ApiResponse<List<SimplePostRecord>> getPostRecords(@PathVariable("postUuid") UUID postUuid, Principal principal){

        List<SimplePostRecord> records = postRecordService.getByPostId(postUuid);

        return apiResponseBuilder.createSuccessResponse(records);
    }

    @GetMapping("/{postUuid}/record/{recordUuid}")
    public ApiResponse<SimplePostRecord> getPostRecords(@PathVariable("postUuid") UUID postUuid, @PathVariable("recordUuid") UUID recordUuid) throws Throwable {

        SimplePostRecord record = postRecordService.getByRecordId(recordUuid);

        return apiResponseBuilder.createSuccessResponse(record);

    }

    @PostMapping("/{postUuid}/record")
    public ApiResponse<SimplePostRecord> newPostRecord(
            @PathVariable("postUuid") UUID postUuid,
            @RequestBody RecordRequest recordRequest,
            Principal principal){

        SimplePostRecord newRecord = postRecordService.newRecord(postUuid, recordRequest, principal);

        return apiResponseBuilder.createSuccessResponse(newRecord);
    }

    @PostMapping("/{postUuid}/record/{recordUuid}")
    public ApiResponse<SimplePostRecord> updateRecord(
            @PathVariable("postUuid") UUID postUuid,
            @PathVariable("recordUuid") UUID recordUuid,
            @RequestBody RecordRequest recordRequest,
            Principal principal){

        SimplePostRecord newRecord = postRecordService.updateRecord(postUuid, recordUuid, recordRequest, principal);

        return apiResponseBuilder.createSuccessResponse(newRecord);
    }

    @DeleteMapping("/{postUuid}/record/{recordUuid}")
    public ApiResponse<SimplePostRecord> deleteRecord(
            @PathVariable("postUuid") UUID postUuid,
            @PathVariable("recordUuid") UUID recordUuid,
            Principal principal) throws Throwable {

        postRecordService.deleteRecord(recordUuid);

        return apiResponseBuilder.createSuccessResponse(null);
    }

//    @PostMapping
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    public void quickPost(@RequestBody PostRequest post, Principal principal){
//        exercisePostService.newPost(post, principal);
//    }

}
