package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.ResultRequest;
import com.g11.FresherManage.service.MarketService;
import com.g11.FresherManage.service.PointService;
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
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/v1/points")
@Slf4j
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    @Operation( summary = "Get point By fresher Id",
            description =  "GGet point By fresher Id.",
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
                                          "average": 0,
                                          "lessson1": 0,
                                          "lessson2": 0,
                                          "lessson3": 0
                                        },
                                        "timestamp": "2024-07-30"
                                      }
                                      """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{fresherID}")
    public ResponseEntity<?> getPointByFresherId(@PathVariable("fresherID") Integer fresherID)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        pointService.getPointByFresherId(fresherID))
                , HttpStatus.OK);
    }

    @Operation( summary = "Add point for fresher Id",
            description =  "Add point for fresher Id.",
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
                                          "lessson3": 9
                                        },
                                        "timestamp": "2024-07-30"
                                      }
                                      """))),
    })
    @PreAuthorize("hasAnyRole('MENTOR')")
    @PostMapping("/{fresherId}")
    public ResponseEntity<?> addPointByFresherId(@PathVariable("fresherId") Integer fresherId,
                                                 @Valid @RequestBody ResultRequest resultRequest
                                                 )
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(201,"success",
                        pointService.addPointByFresherId(fresherId,resultRequest))
                , HttpStatus.OK);
    }

    @Operation( summary = "Get point of logged-in fresher",
            description =  "Get point of logged-in fresher.",
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
                                          "average": 0,
                                          "lessson1": 0,
                                          "lessson2": 0,
                                          "lessson3": 0
                                        },
                                        "timestamp": "2024-07-30"
                                      }
                                      """))),
    })
    @PreAuthorize("hasRole('FRESHER')")
    @GetMapping("/me")
    public ResponseEntity<?> findMyPoint(Principal principal)  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        pointService.findPointByUsername(principal.getName()))
                ,HttpStatus.OK);
    }
}
