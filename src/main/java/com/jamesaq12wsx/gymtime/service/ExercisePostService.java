package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ExercisePostRepository;
import com.jamesaq12wsx.gymtime.database.FitnessClubRepository;
import com.jamesaq12wsx.gymtime.database.PostCountRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.*;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
import com.jamesaq12wsx.gymtime.model.payload.UpdatePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ExercisePostService {

    private final ExercisePostRepository exercisePostRepository;

    private final PostCountRepository postCountRepository;

    private final FitnessClubRepository fitnessClubRepository;

    @Autowired
    public ExercisePostService(ExercisePostRepository exercisePostRepository, PostCountRepository postCountRepository, FitnessClubRepository fitnessClubRepository) {
        this.exercisePostRepository = exercisePostRepository;
        this.postCountRepository = postCountRepository;
        this.fitnessClubRepository = fitnessClubRepository;
    }

    public List<ExercisePost> getAllPostByUser(Principal principal) {
        return exercisePostRepository.findAllByAudit_CreatedBy(principal.getName());
    }

    public List<ExercisePost> getAllPostByUserWithYear(String year, Principal principal) {
        return exercisePostRepository.findAllByAudit_CreatedByAndYear(principal.getName(), year);
    }

    public ExercisePost newPost(PostRequest mark, Principal principal) {

        if (!fitnessClubRepository.existsById(mark.getClubUuid())) {
            throw new ApiRequestException(String.format("This club id %s is not exist", mark.getClubUuid()));
        }

        SimpleExercisePost newPost = exercisePostRepository.save(
                new SimpleExercisePost(
                        null,
                        LocalDateTime.now(),
                        mark.getPrivacy() == null ? PostPrivacy.PRIVATE : mark.getPrivacy(),
                        mark.getClubUuid(),
                        mark.getExercises(),
                        new Audit(LocalDateTime.now(), principal.getName(), LocalDateTime.now())));

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

    public ExercisePost update(UpdatePostRequest updatePostRequest, Principal principal) {

        //  TODO: check username with post

        SimpleExercisePost updatePost = exercisePostRepository.findById(updatePostRequest.getUuid()).orElse(null);

        if (updatePost == null){
            throw new ApiRequestException(String.format("Post %s not existed", updatePostRequest.getUuid()));
        }

        return exercisePostRepository.save(
                new SimpleExercisePost(
                        updatePostRequest.getUuid(),
                        updatePostRequest.getPostTime(),
                        updatePostRequest.getPrivacy(),
                        updatePostRequest.getClubUuid(),
                        updatePostRequest.getExercises(),
                        updatePost.getAudit())
        );

    }

    public void delete(UUID uuid, Principal principal) {

        exercisePostRepository.deleteById(uuid);

    }
}
