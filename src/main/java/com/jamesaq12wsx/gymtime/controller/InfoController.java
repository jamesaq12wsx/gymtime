package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.Country;
import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.FitnessClubSelectItem;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
import com.jamesaq12wsx.gymtime.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller get information of the app, such as
 * club list
 * exercise list
 * app version
 * for user select when they edit exercise record
 */
@RestController
@RequestMapping("api/v1/info")
public class InfoController {

    private final FitnessClubService fitnessClubService;

    private final InfoService infoService;

    @Autowired
    public InfoController(FitnessClubService fitnessClubService, InfoService infoService) {
        this.fitnessClubService = fitnessClubService;
        this.infoService = infoService;
    }

    @GetMapping("/select/country")
    public List<Country> getAllCountry(){
        return infoService.getAllCountry();
    }

    @GetMapping("/select/clubs")
    public List<FitnessClubSelectItem> getAllFitnessClubSelectItems(
            @RequestParam(name = "country", required = false) String alphaTwoCode
    ){

        if (alphaTwoCode == null){
            throw new ApiRequestException("No country code");
        }

        return fitnessClubService.getFitnessSelectItems(alphaTwoCode);
    }

    @GetMapping("/select/exercise")
    public List<Exercise> getAllExercise(){
        return infoService.getAllSimpleExercise();
    }
}
