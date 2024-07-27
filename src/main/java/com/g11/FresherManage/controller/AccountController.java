package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @Operation( summary = "Get infor of logged-in user",
            description =  "Retrieve information of the currently logged-in user.  ",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(value = "{\n" +
                    "  \"status\": 200,\n" +
                    "  \"message\": \"success\",\n" +
                    "  \"data\": {\n" +
                    "    \"iduser\": 1,\n" +
                    "    \"username\": \"1\",\n" +
                    "    \"avatar\": \"4\",\n" +
                    "    \"firstName\": \"2\",\n" +
                    "    \"lastName\": \"2\",\n" +
                    "    \"email\": \"2\",\n" +
                    "    \"phone\": \"2\",\n" +
                    "    \"position\": null,\n" +
                    "    \"is_active\": \"2\",\n" +
                    "    \"curentWorking\": \"3,\",\n" +
                    "    \"roles\": [\n" +
                    "      {\n" +
                    "        \"idRole\": 5,\n" +
                    "        \"roleName\": \"ROLE_MARKETDIRECTOR\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"idRole\": 3,\n" +
                    "        \"roleName\": \"ROLE_MENTOR\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"timestamp\": \"2024-07-27\"\n" +
                    "}"))),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/infor")
    public ResponseEntity<?> findInforAccountLogin(Principal principal)
    {
        log.info("(infor Account Login) principal: {}", principal);
        String username = principal.getName();
        ResponseGeneral<?> responseGeneral= ResponseGeneral.of(200,
                "success",accountService.findInforByUsername(username));
        return new ResponseEntity<>(responseGeneral, HttpStatus.OK);
    }
}
