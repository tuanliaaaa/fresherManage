package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.RefreshToken;
import com.g11.FresherManage.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            description = "This endpoint creates a new token with the authen user details."
//            tags = {"User Operations"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User logined successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid  @RequestBody LoginRequest loginRequest)
    {
        log.info("(login) request:{}", loginRequest);
//        System.out.println(new BCryptPasswordEncoder().encode("tuan"));
        ResponseGeneral<LoginResponse> responseGeneral=ResponseGeneral.ofCreated(
    "success",
            accountService.login(loginRequest));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseGeneral<LoginResponse>> refreshToken(
            @Valid  @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        log.info("(refreshToken) request:{}", refreshTokenRequest);
        ResponseGeneral<LoginResponse> responseGeneral=ResponseGeneral.ofCreated(
    "success", accountService.refreshToken(refreshTokenRequest));
        return new ResponseEntity<>(responseGeneral, HttpStatus.CREATED);
    }
}