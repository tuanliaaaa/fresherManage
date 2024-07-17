package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.point.PointResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Result;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ResultRepository;
import com.g11.FresherManage.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointServiceImpl implements PointService {
    private final ResultRepository resultRepository;
    private final AccountRepository accountRepository;
    @Override
    public PointResponse findMyPoint(Principal principal)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
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
    public PointResponse getPointByFresherId(Principal principal, int fresherId)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        switch (userLogining.getPotition()) {
            case "ADMIN":
                break;
            default:
                String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();
                String curentWorkingFresher = fresher.getCurentWorking()==null?"":fresher.getCurentWorking();

                if((curentWorkingFresher.equals("")&&curentWorkingLogining.equals(""))||!curentWorkingLogining.contains(curentWorkingFresher))
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

}
