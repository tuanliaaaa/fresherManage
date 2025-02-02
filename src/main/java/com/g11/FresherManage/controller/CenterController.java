package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.historyWorking.FresherCenterRequest;
import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.dto.response.historyWorking.FresherCenterResponse;
import com.g11.FresherManage.service.CenterService;
import com.g11.FresherManage.service.HistoryWorkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/centers")
@Slf4j
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;
    private final HistoryWorkingService historyWorkingService;

    @Operation( summary = "Get the center where the logged-in user is working",
            description =  "Get the center where the logged-in user is working.If the position is Market Director, get all centers under the markets managed by that person. If the position is Mentor or Center Director, get only the center where that person is working.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 200,
                                      "message": "success",
                                      "data": [
                                        {
                                          "workingId": 2,
                                          "workingName": "Mi1",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        },
                                        {
                                          "workingId": 3,
                                          "workingName": "Mi2",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        }
                                      ],
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN','FRESHER','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyCenterInfor(
            Principal principal)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                centerService.findCenterByUsername(principal.getName())), HttpStatus.OK);
    }

    @Operation( summary = "Get center by centerID",
            description =  "Get center by centerID.If the role of the currently logged-in user is Admin, they can view all information. However, if the role is Market Director, Mentor,Fresher, or Center Director, we can only view the information of the center if we work there.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 200,
                                      "message": "success",
                                      "data": 
                                        {
                                          "workingId": 2,
                                          "workingName": "Mi1",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        },
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('FRESHER','ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{centerId}")
    public ResponseEntity<?> getCenterByCenterId(
        @PathVariable("centerId") Integer centerId)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                centerService.getCenterByCenterId(centerId)), HttpStatus.OK);
    }

    @Operation( summary = "Get all center",
            description =  "Get all center. This api used by ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 200,
                                      "message": "success",
                                      "data": [
                                        {
                                          "workingId": 2,
                                          "workingName": "Mi1",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        },
                                        {
                                          "workingId": 3,
                                          "workingName": "Mi2",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        }
                                      ],
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> findAllCenter(
          @RequestParam(required = false) Integer page)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.findAllCenter(page)), HttpStatus.OK);
    }

    @Operation( summary = "Add center",
            description =  "add center. This api used by ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 201,
                                      "message": "success",
                                      "data":
                                        {
                                          "workingId": 2,
                                          "workingName": "Mi1",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        },
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createCenter(
            @Valid @RequestBody CenterRequest centerRequest
    )
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(201,"success",
                        centerService.createCenter(centerRequest)), HttpStatus.OK);
    }


    @Operation( summary = "Delete center by centerID",
            description =  "Delete center by centerID. If the role of the currently logged-in user is Admin, they can delete this center.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 204,
                                      "message": "success",
                                      "data": null,
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{centerId}")
    public ResponseEntity<?> deleteCenterByCenterId(
            @PathVariable("centerId") Integer centerId
    )
    {
        centerService.deleteCenterByCenterId(centerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation( summary = "Update center by centerID",
            description =  "Update center by centerID. If the role of the currently logged-in user is Admin, they can update this center.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 200,
                                      "message": "success",
                                      "data": 
                                       {
                                          "workingId": 2,
                                          "workingName": "Mi1",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        },
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{centerId}")
    public ResponseEntity<?> updateCenterByCenterId(
            @PathVariable("centerId") Integer centerId,
            @Valid @RequestBody CenterUpdateRequest centerUpdateRequest
    )
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.updateCenterByCenterId(centerId,centerUpdateRequest)), HttpStatus.OK);
    }

    @Operation( summary = "Get List center by marketID",
            description =  "Get List center by marketID. If the role of the currently logged-in user is Admin, they can update this center.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "status": 200,
                                      "message": "success",
                                      "data": 
                                       {
                                          "workingId": 2,
                                          "workingName": "Mi1",
                                          "workingType": "CENTER",
                                          "workingStatus": "1"
                                        },
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/market/{marketId}")
    public ResponseEntity<?> findAllCenterByMarketID( @PathVariable("marketId") Integer marketId)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                centerService.findAllCenterByMarketID(marketId))
                , HttpStatus.OK);
    }

    @Operation( summary = "Add Fresher to Center",
            description =  "Add Fresher to Center. This Api of Admin",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                       "status": 201,
                                       "message": "success",
                                       "data": {
                                         "historyWorkingId": 4,
                                         "fresherId": 4,
                                         "centerID": 3
                                       },
                                       "timestamp": "2024-07-30"
                                     }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{centerId}/freshers")
    public ResponseEntity<?> addFresherToCenter(@PathVariable int centerId, @RequestBody FresherCenterRequest fresherCenterRequest)
    {
        FresherCenterResponse fresher = historyWorkingService.addFresherToCenter(centerId, fresherCenterRequest.getFresherId());
        return new ResponseEntity<>(
                ResponseGeneral.of(201,"success",
                        fresher)
                , HttpStatus.CREATED);
    }

    @Operation( summary = "Change Fresher to new center",
            description =  "Change Fresher to new Center. This Api of Admin",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                       "status": 200,
                                       "message": "success",
                                       "data": {
                                         "historyWorkingId": 4,
                                         "fresherId": 4,
                                         "centerID": 3
                                       },
                                       "timestamp": "2024-07-30"
                                     }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("fresher/{fresherId}/center/{newCenterId}")
    public ResponseEntity<?> transferFresherToCenter(@PathVariable Integer fresherId, @PathVariable Integer newCenterId)
    {
        FresherCenterResponse updatedFresher = historyWorkingService.transferFresherToCenter(fresherId, newCenterId);
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        updatedFresher)
                , HttpStatus.OK);
    }

    @Operation( summary = "Merge two center to one center or one new center",
            description =  "Merge two center to one center or one new center. This Api of Admin",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                       {
                                        "status": 201,
                                        "message": "success",
                                        "data": {
                                          "pooledHistoryId": 2,
                                          "centerEnd": {
                                            "workingId": 2,
                                            "workingName": "Vmo Duy Tân",
                                            "workingType": "CENTER",
                                            "workingStatus": "lock",
                                            "market": {
                                              "workingId": 1,
                                              "workingName": "Việt Nam",
                                              "workingType": "MARKET",
                                              "workingStatus": "active",
                                              "market": null
                                            }
                                          },
                                          "centerPoll": {
                                            "workingId": 3,
                                            "workingName": "Vmo Tôn Thất Thuyết",
                                            "workingType": "CENTER",
                                            "workingStatus": "lock",
                                            "market": {
                                              "workingId": 1,
                                              "workingName": "Việt Nam",
                                              "workingType": "MARKET",
                                              "workingStatus": "active",
                                              "market": null
                                            }
                                          },
                                          "createAt": "2024-08-06T18:35:13.4269979"
                                        },
                                        "timestamp": "2024-08-06"
                                      }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("{centerId}/merge/{centerMergeID}")
    public ResponseEntity<?> mergeCenter(
        @PathVariable Integer centerId,
        @PathVariable Integer centerMergeID
    )
    {
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        centerService.mergerCenter(centerId,centerMergeID ))
                , HttpStatus.OK);
    }



}
