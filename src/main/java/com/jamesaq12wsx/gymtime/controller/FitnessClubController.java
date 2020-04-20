package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.service.PostService;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/clubs")
public class FitnessClubController {

    private final FitnessClubService fitnessClubService;

    private final PostService postService;

    @Autowired
    public FitnessClubController(FitnessClubService fitnessClubService, PostService postService) {
        this.fitnessClubService = fitnessClubService;
        this.postService = postService;
    }

    @GetMapping
    public List<? extends FitnessClub> getAllFitnessClubs(@PathParam("brand") String brandId, @PathParam("country") String country, Principal principal){

        return fitnessClubService.getAllFitnessClubs();
    }

    @GetMapping("/brand/{brandId}")
    public List<? extends FitnessClub> getAllFitnessClubsBYBrand(@PathVariable("brandId") Integer brandId, Principal principal){

        return fitnessClubService.getAllClubsByBrandId(Integer.valueOf(brandId));
    }

    @GetMapping("/country/{countryCode}")
    public List<? extends FitnessClub> getAllFitnessClubsByCountry(@PathVariable("countryCode") String country, Principal principal){

        return fitnessClubService.getAllFitnessByCountry(country);
    }

    @GetMapping("/club/{uuid}")
    public FitnessClub getFitnessByUuid(@PathVariable("uuid") UUID uuid, Principal principal){
        return fitnessClubService.getFitnessById(uuid, principal);
    }

    @GetMapping("/club/{clubUuid}/posts")
    @PreAuthorize("permitAll()")
    public List<PostCount> getClubDailyPostNoDate(@PathVariable("clubUuid") UUID clubUuid){
        return postService.dailyPost(clubUuid, LocalDate.now());
    }

    @GetMapping("/club/{clubUuid}/posts/{date}")
    @PreAuthorize("permitAll()")
    public List<PostCount> getClubDailyPost(@PathVariable("clubUuid") UUID clubUuid, @PathVariable(value = "date") String dateStr){

        try{

            LocalDate date = LocalDate.parse(dateStr);

            return postService.dailyPost(clubUuid, date);

        }catch (DateTimeParseException e){
            e.printStackTrace();

            throw new ApiRequestException(String.format("Date %s cannot parse", dateStr));
        }
    }

}
