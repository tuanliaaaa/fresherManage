package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.account.InforAccountLoginResponse;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.Account;

import java.util.List;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest) ;
    InforAccountLoginResponse findInforByUsername(String username);
    Account findAccountByUsername(String username);
    List<Account> findAllAccounts();
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
