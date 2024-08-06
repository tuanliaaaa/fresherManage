package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.dto.request.market.MarketRequest;
import com.g11.FresherManage.dto.request.market.MarketUpdateRequest;
import com.g11.FresherManage.service.CenterService;
import com.g11.FresherManage.service.MarketService;
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
@RequestMapping("/api/v1/markets")
@Slf4j
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;
    @Operation( summary = "Find all market",
            description =  "Find all market. This api used by ADMIN role.",
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
                                          "workingId": 1,
                                          "workingName": "Mi",
                                          "workingType": "MARKET",
                                          "workingStatus": "1"
                                        },
                                        {
                                          "workingId": 3,
                                          "workingName": "Viet",
                                          "workingType": "MARKET",
                                          "workingStatus": "1"
                                        }
                                      ],
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> findAllMarket(
            @RequestParam(required = false) Integer page)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                    marketService.findAllMarket(page)), HttpStatus.OK);
    }

    @Operation( summary = "Get all centers where the user is currently working.",
            description =  "Get all centers where the user is currently working.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "success",
                                        "data": [
                                            {
                                                "workingId": 1,
                                                "workingName": "Mĩ",
                                                "workingType": "MARKET",
                                                "workingStatus": "active"
                                            },
                                            {
                                                "workingId": 2,
                                                "workingName": "Việt Nam",
                                                "workingType": "MARKET",
                                                "workingStatus": "active"
                                            }
                                        ],
                                        "timestamp": "2024-07-28"
                                    }"""))
            )
    })
    @PreAuthorize("hasAnyRole('FRESHER','MENTOR','ADMIN','CENTERDIRECTOR','MARKETDIRECTOR')")
    @GetMapping("/me")
    public ResponseEntity<?> findMyMarket(Principal principal)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        marketService.findMarketOfUsername(principal.getName())), HttpStatus.OK);
    }

    @Operation( summary = "Get market by market id.",
            description =  "Get market by market id. If the role of the currently logged-in user is Admin, they can view all information. However, if the role is Market Director, Mentor, or Center Director, we can only view the information of the market if we work there..",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "success",
                                        "data": {
                                                "workingId": 1,
                                                "workingName": "Mĩ",
                                                "workingType": "MARKET",
                                                "workingStatus": "active"
                                            },
                                        "timestamp": "2024-07-28"
                                    }"""))
            )
    })
    @PreAuthorize("hasAnyRole('FRESHER','ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{marketId}")
    public ResponseEntity<?> getMarketByMarketId(
         @PathVariable("marketId") Integer marketId)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        marketService.getMarketByMarketId(marketId)), HttpStatus.OK);
    }

    @Operation( summary = "Add market",
            description =  "Add market. This api used by ADMIN role.",
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
                                          "workingName": "Mi",
                                          "workingType": "MARKET",
                                          "workingStatus": "1"
                                        },
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createMarket(
            @Valid @RequestBody MarketRequest marketRequest
    )
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(201,"success",
                        marketService.createMarket(marketRequest)), HttpStatus.OK);
    }


    @Operation( summary = "Delete market by marketId",
            description =  "Delete market by marketId. If the role of the currently logged-in user is Admin, they can delete this market.",
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
    @DeleteMapping("/{marketId}")
    public ResponseEntity<?> deleteMarketByMarketId(
            @PathVariable("marketId") Integer marketId
    )
    {
        marketService.deleteMarketByMarketId(marketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation( summary = "Update market by marketID",
            description =  "Update market by marketID. If the role of the currently logged-in user is Admin, they can update this market.",
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
                                          "workingId": 1,
                                          "workingName": "Mi",
                                          "workingType": "MARKET",
                                          "workingStatus": "1"
                                        },
                                      "timestamp": "2024-07-28"
                                    }
                                    """))),
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{marketId}")
    public ResponseEntity<?> updateMarketByMarketId(
            @PathVariable("marketId") Integer marketId,
            @Valid @RequestBody MarketUpdateRequest marketUpdateRequest
    )
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        marketService.updateMarketByMarketId(marketId,marketUpdateRequest)), HttpStatus.OK);
    }



}
