package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.database.ExercisePostDao;
import com.jamesaq12wsx.gymtime.database.ExercisePostDaoImpl;
import com.jamesaq12wsx.gymtime.database.FitnessClubDao;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import com.jamesaq12wsx.gymtime.model.payload.PostRequest;
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

        if (!fitnessClubDao.exist(mark.getClubUuid())){
            throw new ApiRequestException(String.format("This club id %s is not exist", mark.getClubUuid()));
        }

        exercisePostDao.save(new ExercisePost(null, principal.getName(), LocalDateTime.now(), mark.getPrivacy() == null ? PostPrivacy.PRIVATE : mark.getPrivacy(), mark.getClubUuid(), mark.getExercises()));
    }

    public List<PostCount> dailyPost(UUID clubUuid, LocalDate date) {

        if (!fitnessClubDao.exist(clubUuid)) {
            throw new ApiRequestException(String.format("Club %s not exist", clubUuid.toString()));
        }

        List<PostCount> result = exercisePostDao.getGymHourPost(clubUuid, date == null ? LocalDate.now() : date);

        LocalDateTime startOfDay = date.atStartOfDay();

        Set<Integer> set = new HashSet<>();

        for (PostCount pc: result){
            set.add(pc.getDateTime().getHour());
        }

        for (int i=0; i<24; i++){
            if (!set.contains(i)){

                result.add(new PostCount(LocalDateTime.of(date, LocalTime.of(i, 0)), 0));
            }
        }

        return result;
    }

}
