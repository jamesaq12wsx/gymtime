package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ExercisePostDao;
import com.jamesaq12wsx.gymtime.database.ExercisePostDaoImpl;
import com.jamesaq12wsx.gymtime.database.FitnessClubDao;
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

    private final ExercisePostDao exercisePostDao;

    private final FitnessClubDao fitnessClubDao;

    @Autowired
    public ExercisePostService(ExercisePostDaoImpl exerciseMarkDao, FitnessClubDao fitnessClubDao) {
        this.exercisePostDao = exerciseMarkDao;
        this.fitnessClubDao = fitnessClubDao;
    }

    public List<ExercisePost> getAllPostByUser(Principal principal) {
        return exercisePostDao.getAllMarksByUser(principal.getName());
    }

    public List<ExercisePost> getAllPostByUserWithYear(String year, Principal principal) {
        return exercisePostDao.getAllPostsByUserWithYear(year, principal.getName());
    }

    public void newPost(PostRequest mark, Principal principal) {

        if (!fitnessClubDao.exist(mark.getClubUuid())) {
            throw new ApiRequestException(String.format("This club id %s is not exist", mark.getClubUuid()));
        }

        exercisePostDao.save(new SimpleExercisePostAudit(null, LocalDateTime.now(), mark.getPrivacy() == null ? PostPrivacy.PRIVATE : mark.getPrivacy(), mark.getClubUuid(), mark.getExercises(), principal.getName(), LocalDateTime.now(), LocalDateTime.now()));
    }

    public List<PostCount> dailyPost(UUID clubUuid, LocalDate date) {

        if (!fitnessClubDao.exist(clubUuid)) {
            throw new ApiRequestException(String.format("Club %s not exist", clubUuid.toString()));
        }

        List<PostCount> result = exercisePostDao.getGymHourPost(clubUuid, date == null ? LocalDate.now() : date);

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

    public void update(UpdatePostRequest updatePostRequest, Principal principal) {

        //  TODO: check username with post

        exercisePostDao.update(
                new SimpleExercisePost(
                        updatePostRequest.getUuid(),
                        updatePostRequest.getPostTime(),
                        updatePostRequest.getPrivacy(),
                        updatePostRequest.getClubUuid(),
                        updatePostRequest.getExercises())
        );

    }

    public void delete(UUID uuid, Principal principal) {

        exercisePostDao.delete(uuid);

    }
}
