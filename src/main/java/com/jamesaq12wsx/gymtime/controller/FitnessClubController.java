package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.FitnessClubDetail;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/clubs")
public class FitnessClubController {

    private final FitnessClubService fitnessClubService;

    @Autowired
    public FitnessClubController(FitnessClubService fitnessClubService) {
        this.fitnessClubService = fitnessClubService;
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
    public FitnessClubDetail getFitnessByUuid(@PathVariable("uuid") UUID uuid, Principal principal){
        return fitnessClubService.getFitnessDetail(uuid);
    }

}
