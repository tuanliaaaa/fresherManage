package com.g11.FresherManage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboards")
@Slf4j
@RequiredArgsConstructor
public class DashBoardController {
//    @Operation( summary = "Get the center where the logged-in user is working",
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
//    @GetMapping("/fresher")
//    public String freshers() {
//
//    }
}
