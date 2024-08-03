package com.g11.FresherManage.controller;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboards")
@Slf4j
@RequiredArgsConstructor
public class DashBoardController {
    private final StatistisService statistisService;

//    @Operation( summary = "Get Fresher Statistics",
//            description =  "Get the center where the logged-in user is working.If the position is Market Director, get all centers under the markets managed by that person. If the position is Mentor or Center Director, get only the center where that person is working.",
//            security = @SecurityRequirement(name = "bearerAuth"))
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success",
//                    content = @Content(
//                            mediaType = "application/json",
//                            examples = @ExampleObject(value = """
//                                    {
//                                      "status": 200,
//                                      "message": "success",
//                                      "data": [
//                                        {
//                                          "workingId": 2,
//                                          "workingName": "Mi1",
//                                          "workingType": "CENTER",
//                                          "workingStatus": "1"
//                                        },
//                                        {
//                                          "workingId": 3,
//                                          "workingName": "Mi2",
//                                          "workingType": "CENTER",
//                                          "workingStatus": "1"
//                                        }
//                                      ],
//                                      "timestamp": "2024-07-28"
//                                    }
//                                    """))),
//    })
//    @PreAuthorize("hasAnyRole('ADMIN','FRESHER','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/fresher/amount")
    public ResponseEntity<?> StatisticFresherAmount(
            @RequestParam(value = "workingId",required = false)  Integer workingId,
            @RequestParam(value = "workingType",required = false) String workingType,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
         return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        statistisService.findStatisticFresherAmount(startDate,endDate,workingType,workingId)), HttpStatus.OK);

    }
    @GetMapping("/fresher/point")
    public ResponseEntity<?> StatisticFresherPoint(
            @RequestParam(value = "workingId",required = false)  Integer workingId,
            @RequestParam(value = "workingType",required = false) String workingType,
            @RequestParam(value = "rankPointList",required = true) List<Double> rankPointList,
            @RequestParam(value="typePoint",required = true) String typePoint
    ) {
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        statistisService.findStatisticFresherPoint(rankPointList,typePoint,workingType,workingId)), HttpStatus.OK);

    }
}
