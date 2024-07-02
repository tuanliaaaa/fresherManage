package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/User")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final AccountService accountService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<ResponseGeneral<List<Account>>> findAllUser(){
        ResponseGeneral<List<Account>> responseGeneral= ResponseGeneral.of(200,"success",accountService.findAllAccounts());
        return new ResponseEntity<>(responseGeneral, HttpStatus.OK);
    }
}
