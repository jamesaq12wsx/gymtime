package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.FitnessClub;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
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
@RequestMapping("api/v1/club")
public class FitnessClubController {

    private final FitnessClubService fitnessClubService;

    private final PostService postService;

    @Autowired
    public FitnessClubController(FitnessClubService fitnessClubService, PostService postService) {
        this.fitnessClubService = fitnessClubService;
        this.postService = postService;
    }

//    @GetMapping
//    public ApiResponse<List<? extends FitnessClub>> getAllFitnessClubs(@PathParam("brand") String brandId, @PathParam("country") String country, Principal principal){
//
//        List<? extends FitnessClub> result =  fitnessClubService.getAllFitnessClubs();
//
//        return ApiResponseBuilder.createSuccessResponse(result);
//    }

    @GetMapping
    public ApiResponse<List<? extends FitnessClub>> getClubByLocation(@PathParam("lat") Double lat, @PathParam("lng") Double lng){

        List<? extends FitnessClub> result = fitnessClubService.getClubByLocation(lat, lng);

        return ApiResponseBuilder.createSuccessResponse(result);

    }

    @GetMapping("/brand/{brandId}")
    public ApiResponse<List<? extends FitnessClub>> getAllFitnessClubsBYBrand(@PathVariable("brandId") Integer brandId, Principal principal){

        return ApiResponseBuilder.createSuccessResponse(fitnessClubService.getAllClubsByBrandId(Integer.valueOf(brandId)));
    }

    @GetMapping("/country/{countryCode}")
    public ApiResponse<List<? extends FitnessClub>> getAllFitnessClubsByCountry(@PathVariable("countryCode") String country, Principal principal){

        return ApiResponseBuilder.createSuccessResponse(fitnessClubService.getAllFitnessByCountry(country));
    }

    @GetMapping("/{uuid}")
    public ApiResponse<FitnessClub> getFitnessByUuid(@PathVariable("uuid") UUID uuid, Principal principal){
        return ApiResponseBuilder.createSuccessResponse(fitnessClubService.getFitnessById(uuid, principal));
    }

//    @GetMapping("/{clubUuid}/post")
//    @PreAuthorize("permitAll()")
//    public List<PostCount> getClubDailyPostNoDate(@PathVariable("clubUuid") UUID clubUuid){
//        return postService.dailyPost(clubUuid, LocalDate.now());
//    }

    @GetMapping("/{clubUuid}/post/{date}")
    @PreAuthorize("permitAll()")
    public ApiResponse<List<PostCount>> getClubDailyPost(@PathVariable("clubUuid") UUID clubUuid, @PathVariable(value = "date") String dateStr){

        try{

            LocalDate date = LocalDate.parse(dateStr);

            return ApiResponseBuilder.createSuccessResponse(postService.dailyPost(clubUuid, date));

        }catch (DateTimeParseException e){
            e.printStackTrace();

            throw new ApiRequestException(String.format("Date %s cannot parse", dateStr));
        }
    }

}
