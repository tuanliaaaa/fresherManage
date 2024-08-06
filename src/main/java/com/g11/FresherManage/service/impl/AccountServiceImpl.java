package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.account.InforAccountLoginResponse;
import com.g11.FresherManage.dto.response.LoginResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.RefreshToken;
import com.g11.FresherManage.exception.account.AccountIsLockException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.RefreshTokenRepository;
import com.g11.FresherManage.security.JwtUtilities;
import com.g11.FresherManage.service.AccountService;
import  com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.service.FileService;
import com.g11.FresherManage.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtilities jwtUtilities;

    @Override
    public LoginResponse login(LoginRequest loginRequest)
    {

            Account account = accountRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(UsernameNotFoundException::new);

            // Encrypt the password entered by the user according to the character string stored in the account's database.
            String salt = account.getPassword().substring(0,29);
            String hashedPassword = BCrypt.hashpw(loginRequest.getPassword(), salt);
            if (!hashedPassword.equals(account.getPassword())) {
                throw new UsernameNotFoundException();
            }

            //Check the operational status of account.
            if(account.getIs_active().equals("lock"))throw new AccountIsLockException();

            //Generate an access token and a refresh token.
            String token = jwtUtilities.generateToken(account.getUsername());
            String refreshToken= jwtUtilities.generateRefreshToken(account.getUsername());
            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setRefreshToken(refreshToken);
            refreshTokenEntity.setAccount(account);

            //Save refreshToken to Database
            refreshTokenService.saveToken(refreshTokenEntity);
            return new LoginResponse(token,refreshToken);
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
        //Get the refresh token and check it
        LoginResponse loginResponse =new LoginResponse();
        loginResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
        jwtUtilities.validateToken(refreshTokenRequest.getRefreshToken());
        String username = jwtUtilities.extractUsername(refreshTokenRequest.getRefreshToken());

        //Check the operational status of account.
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(UsernameNotFoundException::new);
        if(account.getIs_active().equals("lock"))throw new AccountIsLockException();

        //Generate an new access token.
        String token = jwtUtilities.generateToken(account.getUsername());
        loginResponse.setAccessToken(token);
        return loginResponse;
    }

    @Override
    public InforAccountLoginResponse findInforByUsername(String username)
    {
        List<Object[]> results= accountRepository.findInforByUsernameWithRoles(username);
        if(results.isEmpty()) throw new UsernameNotFoundException();
        InforAccountLoginResponse inforAccountLoginResponse = new InforAccountLoginResponse(results);
        return inforAccountLoginResponse ;
    }
}