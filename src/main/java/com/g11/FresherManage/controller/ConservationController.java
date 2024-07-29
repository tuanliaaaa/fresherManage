package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.service.ChatService;
import com.g11.FresherManage.service.ConservationService;
import com.g11.FresherManage.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/conservations")
@Slf4j
@RequiredArgsConstructor
public class ConservationController {
    private final ConservationService conservationService;
    private final MessageService messageService;
    @Operation( summary = "Find All conservation by logged-in user.",
            description =  "Find All conservation by logged-in user.",
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
                           "data": [
                             {
                               "idConservation": 2,
                               "conservationName": "hop thaoi 2"
                             },
                             {
                               "idConservation": 1,
                               "conservationName": "hộp thaoij 1"
                             }
                           ],
                           "timestamp": "2024-07-29"
                         }
                    """))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<?> findAllMyConservations(
            Principal principal)
    {
        log.info("findAllMyConservations :{}", principal.getName());
        return new ResponseEntity<>(ResponseGeneral.of(200,
                "success",
                conservationService.findAllConservationsByUsername(principal.getName())), HttpStatus.OK);
    }

    @Operation( summary = "Find All mesage by ConservationID",
            description =  "Find All mesage by ConservationID.",
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
                          "data": [
                            {
                              "idMessage": 1,
                              "message": "ahihi",
                              "sender": 1,
                              "conservation": 1,
                              "is_view": "1",
                              "status": "1"
                            }
                          ],
                          "timestamp": "2024-07-29"
                        }
                    """))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat/{conservationId}")
    public ResponseEntity<?> findChatByConservationId(
            Principal principal,
            @Param("conservationId") Integer conservationId,
            @RequestParam(defaultValue = "0") Integer page)
    {
        log.info("find Chat By Conservation Id :{} of User: {}", conservationId, principal.getName());
        return new ResponseEntity<>(ResponseGeneral.of(200,
                "success",
                messageService.findMessageByConservationId(conservationId,page)), HttpStatus.OK);
    }
}
