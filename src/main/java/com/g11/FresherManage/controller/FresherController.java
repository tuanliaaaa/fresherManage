package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.fresher.FresherRequest;
import com.g11.FresherManage.dto.request.fresher.FresherUpdateRequest;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.service.FresherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
@RequestMapping("/api/v1/freshers")
@Slf4j
@RequiredArgsConstructor
public class FresherController {
    private final FresherService fresherService;

    //------------------- Get Infor of Fresher Loging------------------------------
    @Operation( summary = "Get infor of logged-in Fresher",
            description =  "Retrieve information of the currently logged-in Fresher.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 200,\n" +
                                    "  \"message\": \"success\",\n" +
                                    "  \"data\": {\n" +
                                    "    \"idUser\": 1,\n" +
                                    "    \"username\": \"1\",\n" +
                                    "    \"avatar\": \"1\",\n" +
                                    "    \"firstName\": \"1\",\n" +
                                    "    \"lastName\": \"1\",\n" +
                                    "    \"email\": \"1\",\n" +
                                    "    \"phone\": \"1\",\n" +
                                    "    \"position\": \"FRESHER\",\n" +
                                    "    \"is_active\": \"1\",\n" +
                                    "    \"curentWorking\": \"1,\"\n" +
                                    "  },\n" +
                                    "  \"timestamp\": \"2024-07-27\"\n" +
                                    "}"))
                    )
    })
    @PreAuthorize("hasRole('FRESHER')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyFresherInfo(Principal principal)
    {

        log.info("(getMyFresherInfo) username: {}", principal);
        String username = principal.getName();
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.getFresherByUsername(username)),
                        HttpStatus.OK);
    }

    //------------------ Get Detail Fresher By Fresher ID -------------------------
    @Operation( summary = "Get infor of Fresher By Fresher ID",
            description =  "Get infor of Fresher By Fresher ID. If the role of the currently logged-in user is Admin, they can view all information. However, if the role is Market Director, Mentor, or Center Director, they can only view the information if the fresher belongs to the center where the user works.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =  """
                    {
                      "status": 200,
                      "message": "success",
                      "data": {
                        "idUser": 1,
                        "username": "1",
                        "avatar": "1",
                        "firstName": "1",
                        "lastName": "1",
                        "email": "1",
                        "phone": "1",
                        "position": "FRESHER",
                        "is_active": "1",
                        "curentWorking": "1,"
                      },
                      "timestamp": "2024-07-27"
                    }
                """))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{fresherId}")
    public ResponseEntity<?> getFresherByFresherId(@PathVariable Integer fresherId)
    {
        log.info("(getFresherByFresherId) fresherId: {}", fresherId);
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.getFresherByFresherId(fresherId)),
                HttpStatus.OK);
    }

    //------------------ Get List Fresher By Center ID -------------------------
//    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
//    @GetMapping("/center/{centerId}")
//    public ResponseEntity<?> findFresherByWorkingId(@PathVariable Integer centerId,@RequestParam(defaultValue = "0") Integer page)
//    {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        fresherService.findFresherByWorkingId(principal,workingId, page)),
//                HttpStatus.OK);
//    }





    //------------------- get All Fresher ----------------------------------------
    @Operation( summary = "Get List Fresher ",
            description =  "Get List Fresher By Fresher ID. If the role of the currently logged-in user is Admin, they can view all information. However, if the role is Market Director, Mentor, or Center Director, they can only view the information if the fresher belongs to the center where the user works.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =  """
                    {
                      "status": 200,
                      "message": "success",
                      "data": [{
                        "idUser": 1,
                        "username": "1",
                        "avatar": "1",
                        "firstName": "1",
                        "lastName": "1",
                        "email": "1",
                        "phone": "1",
                        "position": "FRESHER",
                        "is_active": "1",
                        "curentWorking": "1,"
                      }],
                      "timestamp": "2024-07-27"
                    }
                """))
            )
    })
    @Parameters({
            @Parameter(name = "page", description = "Page", in = ParameterIn.QUERY, example = "0")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("")
    public ResponseEntity<?> findAllFreshers(@RequestParam(defaultValue = "0") Integer page)
    {
        log.info("findAllFreshers with page:{}",page);
        return new ResponseEntity<>(ResponseGeneral.of(200,"success",fresherService.findAllFreshers(page)), HttpStatus.OK);
    }

    //------------------- Delete Fresher By Fresher ID----------------------------
    @Operation( summary = "Delete Fresher By Fresher ID",
            description =  "Delete Fresher By Fresher ID. If the role of the currently logged-in user is Admin, they can delete this fresher.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =  """
                        {
                          "status": 204,
                          "message": "success",
                          "data": null,
                          "timestamp": "2024-07-27"
                        }
                    """))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{fresherId}")
    public ResponseEntity<?> deleteFrdesherByFresherId(@PathVariable("fresherId") Integer fresherId)
    {
        log.info("(DeleteFresherByFresherId) fresherId: {}", fresherId);
        fresherService.deleteFrdesherByFresherId(fresherId) ;
        return new ResponseEntity<>(
                ResponseGeneral.ofSuccess("Delete Fresher Success"),HttpStatus.NO_CONTENT);
    }


    //------------------- Create Fresher-----------------------------------------
    @Operation( summary = "Add New Fresher ",
            description =  "Add New Fresher. If the role of the currently logged-in user is Admin, they can view add new Fresher.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =  """
                    {
                      "status": 200,
                      "message": "success",
                      "data": {
                        "idUser": 1,
                        "username": "1",
                        "avatar": "1",
                        "firstName": "1",
                        "lastName": "1",
                        "email": "1",
                        "phone": "1",
                        "position": "FRESHER",
                        "is_active": "1",
                        "curentWorking": "1,"
                      },
                      "timestamp": "2024-07-27"
                    }
                """))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createFresher(@Valid @RequestBody FresherRequest fresherRequest)
    {
        log.info("(FresherRequest) fresherRequest: {}", fresherRequest);
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        fresherService.createFresher(fresherRequest)),HttpStatus.CREATED);
    }

    //------------------- Update Fresher-----------------------------------------
    @Operation( summary = "Update infor of Fresher By Fresher ID",
            description =  "Update infor of Fresher By Fresher ID. If the role of the currently logged-in user is Admin, they can Update this Fresher.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =  """
                    {
                      "status": 200,
                      "message": "success",
                      "data": {
                        "idUser": 1,
                        "username": "1",
                        "avatar": "1",
                        "firstName": "1",
                        "lastName": "1",
                        "email": "1",
                        "phone": "1",
                        "position": "FRESHER",
                        "is_active": "1",
                        "curentWorking": "1,"
                      },
                      "timestamp": "2024-07-27"
                    }
                """))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{fresherId}")
    public ResponseEntity<?> updateFresher(
            @PathVariable Integer fresherId,
            @Valid @RequestBody FresherUpdateRequest fresherUpdateRequest)
    {
        log.info("Fresher {} update request: {}", fresherId ,fresherUpdateRequest);
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        fresherService.updateFresher(fresherId,fresherUpdateRequest)),HttpStatus.OK);
    }


    //------------------ Search Fresher ------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<?> searchFreshers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email

    ) {
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        fresherService.searchFreshers(firstName,lastName,phone,email)),HttpStatus.OK);
    }
}
