package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.service.CenterService;
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
        log.info("getMyCenterInfor {}", principal);
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
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{centerId}")
    public ResponseEntity<?> getCenterByCenterId(
        @PathVariable("centerId") Integer centerId)
    {
        log.info("getCenterByCenterId {}", centerId);
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
        log.info(" findAllCenter with page {}", page);
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
        log.info("add Center : {}",centerRequest);
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
        log.info("deleteCenterByCenterId {}", centerId);
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
        log.info("update center by centerId", centerId);
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.updateCenterByCenterId(centerId,centerUpdateRequest)), HttpStatus.OK);
    }





//    @GetMapping("/market/{marketId}")
//    public ResponseEntity<?> findAllCenterByMarketID(Principal principal, @PathVariable("marketId") Integer marketId)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        , HttpStatus.OK);
//    }
}
