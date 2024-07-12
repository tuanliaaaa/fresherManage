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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/infor")
    public ResponseEntity<?> findInforAccountLogin(Principal principal){
        ResponseGeneral<?> responseGeneral= ResponseGeneral.of(200,"success",accountService.findInforAccountLogin(principal));
        return new ResponseEntity<>(responseGeneral, HttpStatus.OK);
    }
}
