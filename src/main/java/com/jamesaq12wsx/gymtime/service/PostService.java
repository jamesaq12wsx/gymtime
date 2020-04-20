package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.PostRepository;
import com.jamesaq12wsx.gymtime.database.FitnessClubRepository;
import com.jamesaq12wsx.gymtime.database.PostCountRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.*;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.Audit;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.model.payload.UpdatePostRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostCountRepository postCountRepository;

    private final FitnessClubRepository fitnessClubRepository;

    private final ApplicationUserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostCountRepository postCountRepository, FitnessClubRepository fitnessClubRepository, ApplicationUserRepository userRepository) {
        this.postRepository = postRepository;
        this.postCountRepository = postCountRepository;
        this.fitnessClubRepository = fitnessClubRepository;
        this.userRepository = userRepository;
    }

    public List<? extends Post> getAllPostByUser(Principal principal) {
        return postRepository.findAllByUserEmail(principal.getName());
    }

    @GetMapping("/{year}")
    public List<Post> getAllPostByUserWithYear(@PathVariable("year") String year, Principal principal) {
        return postRepository.findAllByAudit_CreatedByAndYear(principal.getName(), year);
    }

    public SimplePost newPost(PostRequest mark, Principal principal) {

        SimpleFitnessClub postClub = fitnessClubRepository.findById(mark.getClubUuid()).orElseThrow(() -> new ApiRequestException(String.format("This club id %s is not exist", mark.getClubUuid())));

        ApplicationUser user = userRepository.findByEmail(principal.getName()).get();

        SimplePost newPost = postRepository.save(
                new SimplePost(
                        null,
                        OffsetDateTime.now(),
                        mark.getPrivacy() == null ? PostPrivacy.PRIVATE : mark.getPrivacy(),
                        postClub,
                        user,
                        new Audit()));

        return newPost;
    }

    public List<PostCount> dailyPost(UUID clubUuid, LocalDate date) {

        if (!fitnessClubRepository.existsById(clubUuid)) {
            throw new ApiRequestException(String.format("Club %s not exist", clubUuid.toString()));
        }

        List<PostCount> result = postCountRepository.findAllByClub(clubUuid, date == null ? LocalDate.now() : date);

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

    public SimplePost update(UpdatePostRequest updatePostRequest, Principal principal) {

        //  TODO: check username with post

        SimplePost updatePost = postRepository
                .findById(updatePostRequest.getPostUuid())
                .orElseThrow(() -> new ApiRequestException(String.format("Post %s not existed", updatePostRequest.getPostUuid())));

        if (updatePostRequest.getClubUuid() != updatePost.getClub().getClubUuid()){
            SimpleFitnessClub club = fitnessClubRepository.findById(updatePostRequest.getClubUuid()).orElse(null);

            if(club != null){
                updatePost.setClub(club);
            }
        }

        if (updatePostRequest.getExerciseTime().equals(updatePost.getExerciseTime())){
            updatePost.setExerciseTime(updatePostRequest.getExerciseTime());
        }

        if (updatePostRequest.getPrivacy() != updatePost.getPrivacy()){
            updatePost.setPrivacy(updatePostRequest.getPrivacy());
        }


        return postRepository.save(updatePost);

    }

    public void delete(UUID uuid, Principal principal) {

        postRepository.deleteById(uuid);

    }
}
