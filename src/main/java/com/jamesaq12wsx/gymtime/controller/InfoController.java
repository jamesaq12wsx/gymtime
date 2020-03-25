package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.FitnessClubSelectItem;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
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

    @Autowired
    public InfoController(FitnessClubService fitnessClubService) {
        this.fitnessClubService = fitnessClubService;
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

}
