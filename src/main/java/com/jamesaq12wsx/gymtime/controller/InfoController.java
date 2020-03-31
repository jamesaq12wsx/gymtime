package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.BrandWithCountry;
import com.jamesaq12wsx.gymtime.model.SimpleCountry;
import com.jamesaq12wsx.gymtime.model.Exercise;
import com.jamesaq12wsx.gymtime.model.FitnessClubSelectItem;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
import com.jamesaq12wsx.gymtime.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/select/brand")
    @PreAuthorize("hasAuthority('info:read')")
    public List<BrandWithCountry> getAllBrand(){
        return infoService.getAllBrand();
    }

    @GetMapping("/select/country")
    @PreAuthorize("hasAuthority('info:read')")
    public List<SimpleCountry> getAllCountry(){
        return infoService.getAllCountry();
    }

    @GetMapping("/select/club")
    @PreAuthorize("hasAuthority('info:read')")
    public List<FitnessClubSelectItem> getAllFitnessClubSelectItems(
            @RequestParam(name = "country", required = false) String alphaTwoCode
    ){

        if (alphaTwoCode == null){
            throw new ApiRequestException("No country code");
        }

        return fitnessClubService.getFitnessSelectItems(alphaTwoCode);
    }

    @GetMapping("/select/exercise")
    @PreAuthorize("hasAuthority('info:read')")
    public List<Exercise> getAllExercise(){
        return infoService.getAllSimpleExercise();
    }
}
