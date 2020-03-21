package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.model.payload.ClubPostHourCount;
import com.jamesaq12wsx.gymtime.service.ExercisePostService;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/clubs")
public class FitnessClubController {

    private final FitnessClubService fitnessClubService;

    private final ExercisePostService exercisePostService;

    @Autowired
    public FitnessClubController(FitnessClubService fitnessClubService, ExercisePostService exercisePostService) {
        this.fitnessClubService = fitnessClubService;
        this.exercisePostService = exercisePostService;
    }

    @GetMapping
    public List<FitnessClub> getAllFitnessClubs(Principal principal){
        return fitnessClubService.getAllFitnessClubs();
    }

    @GetMapping("/location")
    public List<FitnessClub> getAllFitnessClubsWithLocation(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            Principal principal
    ){
        return fitnessClubService.getAllFitnessClubsWithLocation(latitude, longitude);
    }

    @GetMapping("/club/{uuid}")
    public FitnessClub getFitnessByUuid(@PathVariable("uuid") UUID uuid, Principal principal){
        return fitnessClubService.getFitnessDetail(uuid);
    }

    @GetMapping("/club/{clubUuid}/posts")
    @PreAuthorize("permitAll()")
    public List<PostCount> getClubDailyPostNoDate(@PathVariable("clubUuid") UUID clubUuid){
        return exercisePostService.dailyPost(clubUuid, LocalDate.now());
    }

    @GetMapping("/club/{clubUuid}/posts/{date}")
    @PreAuthorize("permitAll()")
    public List<PostCount> getClubDailyPost(@PathVariable("clubUuid") UUID clubUuid, @PathVariable(value = "date") String dateStr){

        try{

            LocalDate date = LocalDate.parse(dateStr);

            return exercisePostService.dailyPost(clubUuid, date);

        }catch (DateTimeParseException e){
            e.printStackTrace();

            throw new ApiRequestException(String.format("Date %s cannot parse", dateStr));
        }
    }

}
