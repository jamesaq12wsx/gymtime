package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.*;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.*;
import com.jamesaq12wsx.gymtime.model.entity.*;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.model.payload.UpdatePostRequest;
import com.jamesaq12wsx.gymtime.service.dto.PostDto;
import com.jamesaq12wsx.gymtime.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostRecordRepository postRecordRepository;

    private final PostCountRepository postCountRepository;

    private final FitnessClubRepository fitnessClubRepository;

    private final ApplicationUserRepository userRepository;

    private final PostMapper postMapper;

    @Autowired
    public PostService(PostRepository postRepository, PostRecordRepository postRecordRepository, PostCountRepository postCountRepository, FitnessClubRepository fitnessClubRepository, ApplicationUserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postRecordRepository = postRecordRepository;
        this.postCountRepository = postCountRepository;
        this.fitnessClubRepository = fitnessClubRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    public List<PostDto> getAllPostByUser(Principal principal) {

        List<Post> posts = postRepository.findAllByUsername(principal.getName());

        return postMapper.toDto(posts);
    }

    public List<PostDto> getAllPostByUserWithYear(String year, Principal principal) {
        return postMapper.toDto(postRepository.findAllByAudit_CreatedByAndYear(principal.getName(), year));
    }

    public PostDto getPostById(Long postId, Principal principal){
        return postMapper.toDto(postRepository.findById(postId).orElseThrow(() -> new ApiRequestException(String.format("Post %s not found", postId))));
    }

    public PostDto newPost(PostRequest mark, Principal principal) {

        FitnessClub postClub = fitnessClubRepository.findById(mark.getClubId()).orElseThrow(() -> new ApiRequestException(String.format("This club id %s is not exist", mark.getClubId())));

        User user = userRepository.findByEmail(principal.getName()).get();

        Post newPost = new Post(null, mark.getExerciseTime(), postClub, null, user);

        newPost.setCreatedBy(user.getEmail());

        Post savePost = postRepository.save(newPost);

        return postMapper.toDto(savePost);
    }

    public List<PostCount> dailyPost(Long clubId, LocalDate date) {

        if (!fitnessClubRepository.existsById(clubId)) {
            throw new ApiRequestException(String.format("Club %s not exist", clubId.toString()));
        }

        List<PostCount> result = postCountRepository.findAllByClub(clubId, date == null ? LocalDate.now() : date);

        LocalDateTime startOfDay = date.atStartOfDay();

        Set<Integer> set = new HashSet<>();

        for (PostCount pc : result) {
            set.add(pc.getDateTime().getHour());
        }

        for (int i = 0; i < 24; i++) {
            if (!set.contains(i)) {

                result.add(new PostCount(LocalDateTime.of(date, LocalTime.of(i, 0)), 0));
            }
        }

        return result;
    }

    public PostDto update(UpdatePostRequest updatePostRequest, Principal principal) {

        //  TODO: check username with post

        Post updatePost = postRepository
                .findById(updatePostRequest.getPostId())
                .orElseThrow(() -> new ApiRequestException(String.format("Post %s not existed", updatePostRequest.getPostId())));

        if (updatePostRequest.getClubId() != updatePost.getClub().getId()){
            FitnessClub club = fitnessClubRepository.findById(updatePostRequest.getClubId()).orElse(null);

            if(club != null){
                updatePost.setClub(club);
            }
        }

        if (updatePostRequest.getExerciseTime().equals(updatePost.getExerciseTime())){
            updatePost.setExerciseTime(updatePostRequest.getExerciseTime());
        }


        return postMapper.toDto(postRepository.save(updatePost));

    }

    public void delete(Long postId, Principal principal) {

        List<PostRecord> records = postRecordRepository.findAllByPostUuid(postId);

        postRecordRepository.deleteAll(records);

        postRepository.deleteById(postId);

    }
}
