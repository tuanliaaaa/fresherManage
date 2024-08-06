package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.entity.RefreshToken;
import com.g11.FresherManage.repository.RefreshTokenRepository;
import com.g11.FresherManage.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public void saveToken(RefreshToken refreshToken)
    {
        refreshTokenRepository.save(refreshToken);
    }
}
