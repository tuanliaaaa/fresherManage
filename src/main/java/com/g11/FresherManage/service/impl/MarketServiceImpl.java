package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.dto.request.market.MarketRequest;
import com.g11.FresherManage.dto.request.market.MarketUpdateRequest;
import com.g11.FresherManage.dto.response.center.CenterResponse;
import com.g11.FresherManage.dto.response.market.MarketResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.center.CenterNotFoundException;
import com.g11.FresherManage.exception.market.MarketNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.AccountRoleService;
import com.g11.FresherManage.service.MarketService;
import com.g11.FresherManage.utils.MapperUtils;
import com.g11.FresherManage.utils.UpdateUtils;
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
    private final AccountRoleService accountRoleService;

    @Override
    public List<MarketResponse> findMarketOfUsername(String username)
    {
        Account userLogining = accountRepository.findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List <Working> workingList = new ArrayList<>();
        // Retrieve all the markets where this person is working.
        for (String workingId:userLogining.getCurentWorking().split(","))
            if(workingId.endsWith("*"))
            {
                workingId = workingId.substring(0, workingId.length() - 1);
                Working market=workingRepository.getMarketByMarketId(Integer.parseInt(workingId)).orElseThrow(
                        ()->new MarketNotFoundException()
                );
                workingList.add(market);
            }
        List<MarketResponse> marketResponseList = MapperUtils.toDTOs(workingList,MarketResponse.class);
        return marketResponseList;
    }


    @Override
    public MarketResponse getMarketByMarketId( Integer marketID)
    {
        //Get list role of account logging
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        if(!roleList.contains("ROLE_ADMIN"))
        {
            //Check if this person is working in the market.
            String username = accountRoleService.getUsername();
            if (username == null) throw new UnauthorizedException();
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );
            String workingIds = userLogining.getCurentWorking() == null ? "" : userLogining.getCurentWorking();
            if (!workingIds.contains(String.valueOf(marketID) + "*,"))
                throw new EmployeeNotWorkinWorkingException();
        }
        Working center = workingRepository.getMarketByMarketId(marketID).orElseThrow(
                ()-> new MarketNotFoundException()
        );
        MarketResponse marketResponse= MapperUtils.toDTO(center,MarketResponse.class);
        return  marketResponse;
    }


    @Override
    public List<MarketResponse> findAllMarket(Integer page)
    {
        List<Working> centerList = workingRepository.findAllMarket(page*10,(page+1)*10);
        List<MarketResponse> marketResponseList= MapperUtils.toDTOs(centerList,MarketResponse.class);
        return marketResponseList;
    }



    @Override
    public void deleteMarketByMarketId(Integer marketId)
    {
        Working market=workingRepository.getMarketByMarketId(marketId).orElseThrow(
                () -> new MarketNotFoundException()
        );
        market.setWorkingStatus("lock");
        workingRepository.save(market);
    }

    @Override
    public MarketResponse updateMarketByMarketId(Integer marketId, MarketUpdateRequest marketUpdateRequest)
    {
        Working market=workingRepository.getCenterByCenterId(marketId).orElseThrow(
                () -> new MarketNotFoundException()
        );
        UpdateUtils.updateEntityFromDTO(market,marketUpdateRequest);
        workingRepository.save(market);
        MarketResponse marketResponse = MapperUtils.toDTO(market,MarketResponse.class);
        return marketResponse;

    }

    @Override
    public MarketResponse createMarket(MarketRequest marketRequest)
    {
        Working marketNew = new Working(
                marketRequest.getWorkingName()
        );
        workingRepository.save(marketNew);
        MarketResponse marketResponse = MapperUtils.toDTO(marketNew,MarketResponse.class);
        return marketResponse;
    }



}
