package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Conservation;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.repository.AccountConservationRepository;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ConservationRepository;
import com.g11.FresherManage.service.ConservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConservationServiceImpl implements ConservationService {
    private final AccountConservationRepository accountConservationRepository;
    private final AccountRepository accountRepository;
    @Override
    public List<Conservation> findAllMyConservations(String username)
    {
        Account userLogining = accountRepository.findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        return accountConservationRepository.findConservationsByAccount(userLogining);

    }


}
