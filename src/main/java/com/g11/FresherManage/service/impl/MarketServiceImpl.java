package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.CenterResponse;
import com.g11.FresherManage.dto.response.MarketResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.MarketService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketServiceImpl implements MarketService {
    private final WorkingRepository workingRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<MarketResponse> findMyMarket(Principal principal)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List <Working> workingList = new ArrayList<>();

        for (String workingId:userLogining.getCurentWorking().split(","))
            workingList.add(workingRepository.getById(Integer.parseInt(workingId)));

        return MapperUtils.toDTOs(workingList,MarketResponse.class);
    }


    @Override
    public MarketResponse getMarketByMarketId(Principal principal, Integer marketID)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        String workingIds = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();
        if(!workingIds.contains(String.valueOf(marketID)+","))
            throw  new EmployeeNotWorkinWorkingException();

        Working center = workingRepository.getById(marketID);
        return  MapperUtils.toDTO(center,MarketResponse.class);
    }


    @Override
    public List<MarketResponse> findAllMarket(Principal principal,Integer page)
    {
        List<Working> centerList = workingRepository.findAllMarket(page*10,(page+1)*10);
        return  MapperUtils.toDTOs(centerList,MarketResponse.class);
    }



}
