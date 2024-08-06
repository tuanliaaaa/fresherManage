package com.g11.FresherManage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.service.StatistisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboards")
@Slf4j
@RequiredArgsConstructor
public class DashBoardController {
    private final StatistisService statistisService;

    @Operation( summary = "Retrieve the number of Freshers by date for the entire company, a center, or a market.",
            description =  "If workingType is null, you will retrieve the total number of freshers for each day. If workingType is Market, you will retrieve the total number of freshers in the market for each day. If workingType is Center, you will retrieve the total number of freshers in the center for each day.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [{
                                            "status": 200,
                                            "message": "success",
                                            "data": [
                                                {
                                                    "date": "2024-07-02",
                                                    "amount": 0
                                                },
                                                {
                                                    "date": "2024-07-03",
                                                    "amount": 0
                                                },
                                                {
                                                    "date": "2024-07-04",
                                                    "amount": 0
                                                },
                                                {
                                                    "date": "2024-07-05",
                                                    "amount": 0
                                                },
                                                {
                                                    "date": "2024-07-06",
                                                    "amount": 0
                                                },
                                      "timestamp": "2024-07-28"
                                    ]
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/fresher/amount")
    public ResponseEntity<?> StatisticFresherAmount(
            @RequestParam(value = "workingId",required = false)  Integer workingId,
            @RequestParam(value = "workingType",required = false) String workingType,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
         return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        statistisService.findStatisticFresherAmount(startDate,endDate,workingType,workingId)), HttpStatus.OK);

    }

    @Operation( summary = "Get the score statistics.",
            description =  "You will pass in a list of score ranges, and it will return the number of freshers corresponding to those scores.You can choose avg or test 1 test 2 test3.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                       "status": 201,
                                       "message": "success",
                                       "data": [
                                         {
                                           "rankPoint": 1,
                                           "amount": 0
                                         },
                                         {
                                           "rankPoint": 10,
                                           "amount": 0
                                         }
                                       ],
                                       "timestamp": "2024-08-06"
                                     }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/fresher/point")
    public ResponseEntity<?> StatisticFresherPoint(
            @RequestParam(value = "rankPointList",required = true) List<Double> rankPointList,
            @RequestParam(value="typePoint",required = true) String typePoint
    ) {
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        statistisService.findStatisticFresherPoint(rankPointList,typePoint)), HttpStatus.OK);

    }

    @Operation( summary = "Get table Dashboad.",
            description =  "Get table Dashboad.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                       "status": 201,
                                       "message": "success",
                                       "data": [
                                         {
                                           "rankPoint": 1,
                                           "amount": 0
                                         },
                                         {
                                           "rankPoint": 10,
                                           "amount": 0
                                         }
                                       ],
                                       "timestamp": "2024-08-06"
                                     }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/tableDashboad")
    public ResponseEntity<?> StatisticTableDashboad(
            @RequestParam(value = "test1", required = false) Double test1,
            @RequestParam(value = "test2", required = false) Double test2,
            @RequestParam(value = "test3", required = false) Double test3,
            @RequestParam(value = "avg", required = false) Double avg,
            @RequestParam(value = "idCenter", required = false) Integer idCenter,
            @RequestParam(value = "idmarket", required = false) Integer idmarket,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        List<Map<String, Integer>> sortList = new ArrayList<>();
        if (sort != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                sortList = objectMapper.readValue(sort, new TypeReference<List<Map<String, Integer>>>() {});
            } catch (JsonProcessingException e) {
                return ResponseEntity.badRequest().body("Invalid sort parameter");
            }
        }
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        statistisService.findStatisticTableDashboad(test1,test2,test3,avg,idCenter,idmarket,sortList)), HttpStatus.OK);

    }
}
