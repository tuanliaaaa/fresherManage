package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.LoginRequest;
import com.g11.FresherManage.dto.response.InforResponse;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.Account;

import java.util.List;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest) ;
    InforResponse infor(Integer idTeam);
    Account findAccountByUsername(String username);
    List<Account> findAllAccounts();
}
