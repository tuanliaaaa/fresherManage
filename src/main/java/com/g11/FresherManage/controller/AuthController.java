package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.RefreshToken;
import com.g11.FresherManage.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auths")
@Slf4j
@RequiredArgsConstructor

public class AuthController {

    private final AccountService accountService;


    @Operation(
            summary = "Login",
            description = "This is the login API using username and password. After logging in, you will receive an access_token and a refresh_token, which are used for authentication and authorization."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User logged in successfully",
                    content = @Content(mediaType = "application/json",
                        examples = @ExampleObject(value = "{\n" +
                            "    \"status\": 201,\n" +
                            "    \"message\": \"success\",\n" +
                            "    \"data\": {\n" +
                            "        \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzIxOTY0NDkzLCJleHAiOjE3MjE5NjgwOTN9.xkLGCxyeiekYVjsCEPxwS8l7xXw-hehsdl96O9-wytk\",\n" +
                            "        \"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzIxOTY0NDkzLCJleHAiOjE3MjE5ODI0OTN9.VFObbC-iihMlt2V4znvhoJ9j5wsfkP3Qa8O-0xecZ-Q\"\n" +
                            "    },\n" +
                            "    \"timestamp\": \"2024-07-26\"\n" +
                            "}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                        examples = @ExampleObject(value = "{\n" +
                                "    \"status\": 400,\n" +
                                "    \"message\": \"validate error\",\n" +
                                "    \"error\": {\n" +
                                "        \"password\": \"password is mandatory\"\n" +
                                "    },\n" +
                                "    \"code\": \"com.g11.FresherManage.exception.validate\",\n" +
                                "    \"timestamp\": \"2024-07-26\"\n" +
                                "}"))),
            @ApiResponse(responseCode = "404", description = "Username or password incorrect",
                    content = @Content(mediaType = "application/json",
                        examples = @ExampleObject(value = "{\n" +
                                "  \"status\": 404,\n" +
                                "  \"message\": \"Not Found\",\n" +
                                "  \"error\": \"User not found\",\n" +
                                "  \"code\": \"com.g11.FresherManage.exception.account.UsernameNotFoundException\",\n" +
                                "  \"timestamp\": \"2024-07-26\"\n" +
                                "}")))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid  @RequestBody LoginRequest loginRequest)
    {
        log.info("(login) by username:{} and password:{}", loginRequest.getUsername(),loginRequest.getPassword());
        ResponseGeneral<LoginResponse> responseGeneral=ResponseGeneral.ofCreated(
    "success",
            accountService.login(loginRequest));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Refresh Token",
            description = "This is an API to obtain a new access_token using a refresh_token when the current access_token expires."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully obtained a new token",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 201,\n" +
                                    "    \"message\": \"success\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzIxOTY0NDkzLCJleHAiOjE3MjE5NjgwOTN9.xkLGCxyeiekYVjsCEPxwS8l7xXw-hehsdl96O9-wytk\",\n" +
                                    "        \"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzIxOTY0NDkzLCJleHAiOjE3MjE5ODI0OTN9.VFObbC-iihMlt2V4znvhoJ9j5wsfkP3Qa8O-0xecZ-Q\"\n" +
                                    "    },\n" +
                                    "    \"timestamp\": \"2024-07-26\"\n" +
                                    "}"))),
            @ApiResponse(responseCode = "400", description = "validate error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"message\": \"validate error\",\n" +
                                    "  \"error\": {\n" +
                                    "    \"refreshToken\": \"refreshToken is mandatory\"\n" +
                                    "  },\n" +
                                    "  \"code\": \"com.cmo.validate\",\n" +
                                    "  \"timestamp\": \"2024-07-27\"\n" +
                                    "}"))),
            @ApiResponse(responseCode = "401", description = "Invalid refresh Token", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" +
                    "  \"status\": 401,\n" +
                    "  \"message\": \"UNAUTHORIZED\",\n" +
                    "  \"error\": \"Invalid JWT signature.\",\n" +
                    "  \"code\": \"com.11.FresherManage.exception.base.UnauthorizedException\",\n" +
                    "  \"timestamp\": \"2024-07-27\"\n" +
                    "}")))
    })
    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseGeneral<LoginResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        log.info("(refreshToken) request:{}", refreshTokenRequest);
        ResponseGeneral<LoginResponse> responseGeneral=ResponseGeneral.ofCreated(
    "success", accountService.refreshToken(refreshTokenRequest));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }
}