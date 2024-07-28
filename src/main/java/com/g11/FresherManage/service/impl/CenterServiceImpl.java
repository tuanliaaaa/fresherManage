package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.dto.response.center.CenterResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.center.CenterNotFoundException;
import com.g11.FresherManage.exception.market.MarketNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.AccountRoleService;
import com.g11.FresherManage.service.CenterService;
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
public class CenterServiceImpl implements CenterService {
    private final WorkingRepository workingRepository;
    private final HistoryWorkingRepository historyWorkingRepository;
    private final AccountRepository accountRepository;
    private final AccountRoleService accountRoleService;
    @Override
    public List<CenterResponse> findCenterByUsername(String username)
    {
        Account userLogining = accountRepository.findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List <Working> workingList = new ArrayList<>();
        if (userLogining.getPosition().equals("MARKETDIRECTOR")) {
            for (String workingId:userLogining.getCurentWorking().split(","))
            if(!workingId.endsWith("*")){
                Working center =workingRepository.getCenterByCenterId(Integer.parseInt(workingId)).orElseThrow(
                        () -> new CenterNotFoundException()
                );
                workingList.add(center);
            }
        }else {
            for (String workingId:userLogining.getCurentWorking().split(","))
                workingList.add(workingRepository.getById(Integer.parseInt(workingId)));
        }
        List<CenterResponse> centerResponseList = MapperUtils.toDTOs(workingList,CenterResponse.class);
        log.info("findCenterByUsername:{}",centerResponseList);
        return centerResponseList ;
    }


    @Override
    public CenterResponse getCenterByCenterId(Integer centerId)
    {

        List<String> roleList=accountRoleService.findRolesByUserLoging();
        if(!roleList.contains("ROLE_ADMIN")) {
            String username = accountRoleService.getUsername();
            if(username == null) throw new UnauthorizedException();
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );
            String workingIds = userLogining.getCurentWorking() == null ? "" : userLogining.getCurentWorking();
            if (!workingIds.contains(String.valueOf(centerId) + ","))
                throw new EmployeeNotWorkinWorkingException();
        }
        Working center = workingRepository.getCenterByCenterId(centerId).orElseThrow(
                ()-> new CenterNotFoundException()
        );
        CenterResponse centerResponse = MapperUtils.toDTO(center,CenterResponse.class);
        log.info("getCenterByCenterId success:{}",centerResponse);
        return  centerResponse;
    }


    @Override
    public List<CenterResponse> findAllCenter(Integer page)
    {
        List<Working> centerList = workingRepository.findAllCenter(page*10,(page+1)*10);
        List<CenterResponse> centerResponses = MapperUtils.toDTOs(centerList,CenterResponse.class);
        log.info("findAllCenter success:{}",centerResponses);
        return  centerResponses;
    }

    @Override
    public void deleteCenterByCenterId(Integer centerId)
    {
        Working center=workingRepository.getCenterByCenterId(centerId).orElseThrow(
                () -> new CenterNotFoundException()
        );
        center.setWorkingStatus("lock");
        workingRepository.save(center);
        log.info("delete success", center);
    }

    @Override
    public CenterResponse updateCenterByCenterId(Integer centerId, CenterUpdateRequest centerUpdateRequest)
    {
        Working center=workingRepository.getCenterByCenterId(centerId).orElseThrow(
                () -> new CenterNotFoundException()
        );
        UpdateUtils.updateEntityFromDTO(center,centerUpdateRequest);
        workingRepository.save(center);
        CenterResponse centerResponse = MapperUtils.toDTO(center,CenterResponse.class);
        log.info("update success", centerResponse);
        return centerResponse;

    }

    @Override
    public CenterResponse createCenter(CenterRequest centerRequest)
    {
        Working market = workingRepository.getMarketByMarketId(centerRequest.getMarketId()).orElseThrow(
                () -> new MarketNotFoundException()
        );
        Working centerNew = new Working(
                centerRequest.getWorkingName(),
                market
        );
        workingRepository.save(centerNew);
        CenterResponse centerResponse = MapperUtils.toDTO(centerNew,CenterResponse.class);
        log.info("create success", centerResponse);
        return centerResponse;
    }

    @Override
    public List<CenterResponse> findAllCenterByMarketID(Principal principal,Integer marketId)
    {
        List<Working> centerList = workingRepository.findByMarket_MarketId(marketId);
        return  MapperUtils.toDTOs(centerList,CenterResponse.class);
    }


}
