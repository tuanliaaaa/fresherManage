package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.InforAccountLoginResponse;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.Account;

import java.security.Principal;
import java.util.List;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest) ;
    InforAccountLoginResponse findInforAccountLogin(Principal principal);
    Account findAccountByUsername(String username);
    List<Account> findAllAccounts();
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
