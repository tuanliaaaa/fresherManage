package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.auth.LoginRequest;
import com.g11.FresherManage.dto.request.auth.RefreshTokenRequest;
import com.g11.FresherManage.dto.response.InforAccountLoginResponse;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtilities jwtUtilities;
    private final FileService fileService;

    @Override
    public LoginResponse login(LoginRequest loginRequest)
    {
            Account account = accountRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(UsernameNotFoundException::new);
            String salt = account.getPassword().substring(0,29);
            String hashedPassword = BCrypt.hashpw(loginRequest.getPassword(), salt);
            if (!hashedPassword.equals(account.getPassword())) {
                throw new UsernameNotFoundException();
            }
            if(account.getIs_active().equals("lock"))throw new AccountIsLockException();
            String token = jwtUtilities.generateToken(account.getUsername());
            String refreshToken= jwtUtilities.generateRefreshToken(account.getUsername());
            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setRefreshToken(refreshToken);
            refreshTokenEntity.setAccount(account);
            refreshTokenRepository.save(refreshTokenEntity);
            return new LoginResponse(token,refreshToken);
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
        LoginResponse loginResponse =new LoginResponse();
        loginResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
        try{
            jwtUtilities.validateToken(refreshTokenRequest.getRefreshToken());
        }catch (Exception e){
            throw new AccountIsLockException();
        }
        String username = jwtUtilities.extractUsername(refreshTokenRequest.getRefreshToken());
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(UsernameNotFoundException::new);
        if(account.getIs_active().equals("lock"))throw new AccountIsLockException();
        String token = jwtUtilities.generateToken(account.getUsername());
        loginResponse.setAccessToken(token);
        return loginResponse;
    }



    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
    }


    @Override
    public List<Account> findAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<Account> response = new ArrayList<>();
        for (Account account : accounts){
            account.setAvatar(fileService.getPhotoURL(account.getAvatar()));
            response.add(account);
        }
        return response;
    }
    @Override
    public InforAccountLoginResponse findInforAccountLogin(Principal principal) {
        Account account= accountRepository.findByUsername(principal.getName()).orElseThrow(UsernameNotFoundException::new);
        InforAccountLoginResponse inforAccountLoginResponse = new InforAccountLoginResponse();
        inforAccountLoginResponse.setAvatar(account.getAvatar());
        inforAccountLoginResponse.setUsername(principal.getName());
        inforAccountLoginResponse.setIs_active(account.getIs_active());
        return inforAccountLoginResponse;
    }
}