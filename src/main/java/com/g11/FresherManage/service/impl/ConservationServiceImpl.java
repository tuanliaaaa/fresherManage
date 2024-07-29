package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.conservation.ConservationResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Conservation;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.repository.AccountConservationRepository;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ConservationRepository;
import com.g11.FresherManage.service.ConservationService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConservationServiceImpl implements ConservationService {
    private final AccountConservationRepository accountConservationRepository;
    @Override
    public List<ConservationResponse> findAllConservationsByUsername(String username)
    {
        List<Conservation> conservations=accountConservationRepository.findConservationsByAccount_Username(username);
        List<ConservationResponse> conservationResponseList = MapperUtils.toDTOs(conservations,ConservationResponse.class);
        log.info("find All Conservations By Username success: {}", conservationResponseList);
        return conservationResponseList;

    }


}
