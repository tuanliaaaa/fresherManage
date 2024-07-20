package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.RefreshToken;
import com.g11.FresherManage.service.AccountService;
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


    @PostMapping("/login")
    public ResponseEntity<ResponseGeneral<LoginResponse>> login(
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