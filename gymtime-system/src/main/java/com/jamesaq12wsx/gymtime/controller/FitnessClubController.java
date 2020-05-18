package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import com.jamesaq12wsx.gymtime.service.PostService;
import com.jamesaq12wsx.gymtime.service.UserDataService;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.entity.FitnessClub;
import com.jamesaq12wsx.gymtime.service.FitnessClubService;
import com.jamesaq12wsx.gymtime.service.dto.FitnessClubDto;
import com.jamesaq12wsx.gymtime.service.dto.UserShortDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/club")
public class FitnessClubController {

    private final FitnessClubService fitnessClubService;

    private final UserDataService userDataService;

    private final PostService postService;

    @Autowired
    public FitnessClubController(FitnessClubService fitnessClubService, UserDataService userDataService, PostService postService) {
        this.fitnessClubService = fitnessClubService;
        this.userDataService = userDataService;
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<List<FitnessClubDto>> getClubByLocation(@PathParam("lat") Double lat, @PathParam("lng") Double lng){

        List<FitnessClubDto> result = fitnessClubService.getClubByLocation(lat, lng);

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

    @GetMapping("/{id}")
    public ApiResponse<FitnessClub> getFitnessByUuid(@PathVariable("id") Long id, Principal principal){
        return ApiResponseBuilder.createSuccessResponse(fitnessClubService.getFitnessById(id, principal));
    }

    @GetMapping("/{id}/recent")
    public ApiResponse<UserShortDto> getRecentUser(@PathVariable("id") Long id, Principal principal){
        if(id == null){
            throw new ApiRequestException(String.format("Club id could not be empty"));
        }

        List<UserShortDto> results = userDataService.getRecentUserByClubId(id, principal);

        return ApiResponseBuilder.createSuccessResponse(results);
    }

    @GetMapping("/{clubId}/post/{date}")
    @PreAuthorize("permitAll()")
    public ApiResponse<List<PostCount>> getClubDailyPost(@PathVariable("clubId") Long clubId, @PathVariable(value = "date") String dateStr){

        try{

            LocalDate date = LocalDate.parse(dateStr);

            return ApiResponseBuilder.createSuccessResponse(postService.dailyPost(clubId, date));

        }catch (DateTimeParseException e){
            e.printStackTrace();

            throw new ApiRequestException(String.format("Date %s cannot parse", dateStr));
        }
    }

}
