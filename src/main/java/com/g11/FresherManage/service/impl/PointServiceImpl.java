package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.ResultRequest;
import com.g11.FresherManage.dto.response.Result.PointResponse;
import com.g11.FresherManage.dto.response.Result.ResultResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Result;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ResultRepository;
import com.g11.FresherManage.service.AccountRoleService;
import com.g11.FresherManage.service.PointService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointServiceImpl implements PointService {
    private final ResultRepository resultRepository;
    private final AccountRepository accountRepository;
    private final AccountRoleService accountRoleService;
    @Override
    public PointResponse findPointByUsername(String username)
    {
        Account userLogining = accountRepository.findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List<Result> points = resultRepository.findByFresher_Id(userLogining.getIdUser());
        PointResponse pointResponse = new PointResponse();
        if(points.size() > 0){
            pointResponse.setLessson1(points.get(0).getTestPoint());
        }
        if(points.size() > 1){
            pointResponse.setLessson1(points.get(1).getTestPoint());
        }
        if(points.size() > 2){
            pointResponse.setLessson2(points.get(2).getTestPoint());
            pointResponse.setAverage((pointResponse.getLessson1()+pointResponse.getLessson2()+pointResponse.getLessson3())/3);
        }
        return pointResponse;
    }

    @Override
    public PointResponse getPointByFresherId(Integer fresherId)
    {

        String username = accountRoleService.getUsername();
        if(username==null) throw new UnauthorizedException();
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        if(!roleList.contains("ROLE_ADMIN"))
        {
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );

            String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();
            String curentWorkingFresher = fresher.getCurentWorking()==null?"":fresher.getCurentWorking();
            if((curentWorkingFresher.equals("")&&curentWorkingLogining.equals("")))
                throw new EmployeeNotWorkinWorkingException();
            List<String> workingIdsLoging = List.of(curentWorkingFresher.split(","))
                    .stream()
                    .filter(id -> id.endsWith("*"))
                    .collect(Collectors.toList());
            if(!curentWorkingLogining.contains(workingIdsLoging.get(0)))
                throw new EmployeeNotWorkinWorkingException();
        }
        List<Result> points = resultRepository.findByFresher_Id(fresher.getIdUser());
        PointResponse pointResponse = new PointResponse();
        if(points.size() > 0){
            pointResponse.setLessson1(points.get(0).getTestPoint());
        }
        if(points.size() > 1){
            pointResponse.setLessson1(points.get(1).getTestPoint());
        }
        if(points.size() > 2){
            pointResponse.setLessson2(points.get(2).getTestPoint());
            pointResponse.setAverage((pointResponse.getLessson1()+pointResponse.getLessson2()+pointResponse.getLessson3())/3);
        }
        return pointResponse;
    }
    @Override
    public ResultResponse addPointByFresherId(Integer fresherId, ResultRequest resultRequest)
    {

        String username = accountRoleService.getUsername();
        if(username==null) throw new UnauthorizedException();
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        Account mentor = accountRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException()
        );
        Result result = new Result(
                mentor,fresher,resultRequest.getPoint()
        );
        result=resultRepository.save(result);
        return MapperUtils.toDTO(result, ResultResponse.class);
    }

}
