package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.CenterResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.CenterService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CenterServiceImpl implements CenterService {
    private final WorkingRepository workingRepository;
    private final HistoryWorkingRepository historyWorkingRepository;
    private final AccountRepository accountRepository;
    @Override
    public List<CenterResponse> findMyCenter(Principal principal)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List <Working> workingList = new ArrayList<>();
        switch (userLogining.getPotition()) {
            case "MARKETDIRECTOR":
                for (String workingId:userLogining.getCurentWorking().split(","))
                workingList.addAll(workingRepository.findByMarket_WorkingId(Integer.parseInt(workingId)));
            default:
                for (String workingId:userLogining.getCurentWorking().split(","))
                workingList.add(workingRepository.getById(Integer.parseInt(workingId)));
        }
        return MapperUtils.toDTOs(workingList,CenterResponse.class);
    }


    @Override
    public CenterResponse getCenterByCenterId(Principal principal,Integer centerId)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        String workingIds = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();
        if(!workingIds.contains(String.valueOf(centerId)+","))
            throw  new  EmployeeNotWorkinWorkingException();

        Working center = workingRepository.getById(centerId);
        return  MapperUtils.toDTO(center,CenterResponse.class);
    }


    @Override
    public List<CenterResponse> findAllCenter(Principal principal,Integer page)
    {
        List<Working> centerList = workingRepository.findAllCenter(page*10,(page+1)*10);
        return  MapperUtils.toDTOs(centerList,CenterResponse.class);
    }


    @Override
    public List<CenterResponse> findAllCenterByMarketID(Principal principal,Integer marketId)
    {
        List<Working> centerList = workingRepository.findByMarket_MarketId(marketIdd );
        return  MapperUtils.toDTOs(centerList,CenterResponse.class);
    }

}
