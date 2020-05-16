package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.entity.Post;
import com.jamesaq12wsx.gymtime.model.entity.PostRecord;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.model.payload.RecordRequest;
import com.jamesaq12wsx.gymtime.model.payload.UpdatePostRequest;
import com.jamesaq12wsx.gymtime.service.PostRecordService;
import com.jamesaq12wsx.gymtime.service.PostService;
import com.jamesaq12wsx.gymtime.service.dto.PostDto;
import com.jamesaq12wsx.gymtime.service.mapper.PostMapper;
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

    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostRecordService postRecordService, ApiResponseBuilder apiResponseBuilder, PostMapper postMapper) {
        this.postService = postService;
        this.postRecordService = postRecordService;
        this.apiResponseBuilder = apiResponseBuilder;
        this.postMapper = postMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<List<PostDto>> getAllUserPost(Principal principal){

        List<PostDto> results = postService.getAllPostByUser(principal);

        return apiResponseBuilder.createSuccessResponse(results);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<Post> getUserPostById(@PathVariable("postId")Long postId, Principal principal){

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

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse deleteExercisePost(@PathVariable("postId") Long postId, Principal principal){

        postService.delete(postId, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @GetMapping("/{postId}/record")
    public ApiResponse<List<PostRecord>> getPostRecords(@PathVariable("postId") Long postId, Principal principal){

        List<PostRecord> records = postRecordService.getByPostId(postId);

        return apiResponseBuilder.createSuccessResponse(records);
    }

    @GetMapping("/{postId}/record/{recordId}")
    public ApiResponse<PostRecord> getPostRecords(@PathVariable("postId") Long postId, @PathVariable("recordId") Long recordId) throws Throwable {

        PostRecord record = postRecordService.getByRecordId(recordId);

        return apiResponseBuilder.createSuccessResponse(record);

    }

    @PostMapping("/{postId}/record")
    public ApiResponse<PostRecord> newPostRecord(
            @PathVariable("postId") Long postId,
            @RequestBody RecordRequest recordRequest,
            Principal principal){

        PostRecord newRecord = postRecordService.newRecord(postId, recordRequest, principal);

        return apiResponseBuilder.createSuccessResponse(newRecord);
    }

    @PutMapping("/{postId}/record/{recordId}")
    public ApiResponse<PostRecord> updateRecord(
            @PathVariable("postId") Long postId,
            @PathVariable("recordId") Long recordId,
            @RequestBody RecordRequest recordRequest,
            Principal principal){

        PostRecord newRecord = postRecordService.updateRecord(postId, recordId, recordRequest, principal);

        return apiResponseBuilder.createSuccessResponse(newRecord);
    }

    @DeleteMapping("/{postId}/record/{recordId}")
    public ApiResponse<PostRecord> deleteRecord(
            @PathVariable("postId") Long postId,
            @PathVariable("recordId") Long recordId,
            Principal principal) throws Throwable {

        postRecordService.deleteRecord(recordId);

        return apiResponseBuilder.createSuccessResponse(null);
    }

}
