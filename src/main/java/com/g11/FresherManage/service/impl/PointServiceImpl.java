package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.ResultRequest;
import com.g11.FresherManage.dto.response.Result.PointResponse;
import com.g11.FresherManage.dto.response.Result.ResultResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Result;
import com.g11.FresherManage.entity.Tutorial;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.result.ResultNotFoundException;
import com.g11.FresherManage.exception.tutorial.MentorNotTutorialThisFresherException;
import com.g11.FresherManage.exception.tutorial.TutorialNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ResultRepository;
import com.g11.FresherManage.repository.TutorialRepository;
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
    private final TutorialRepository tutorialRepository;
    @Override
    public PointResponse findPointByUsername(String username)
    {
        Account userLogining = accountRepository.findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List<Result> points = resultRepository.findByFresherIdAndStatusIs(userLogining.getIdUser());
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
        List<Result> points = resultRepository.findByFresherIdAndStatusIs(fresher.getIdUser());
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
        List<Result> results=resultRepository.findByFresherIdAndStatusIsFalseAndNumberTest(fresherId,resultRequest.getNumberTest());
        if(results.size()==0) throw new ResultNotFoundException();
        List<Tutorial> tutorials =tutorialRepository.findAllByStatusIsAAndFresherIdUser(fresherId);
        if(tutorials.size()==0) throw new TutorialNotFoundException();
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        if(!roleList.contains("ROLE_ADMIN")) {
            String username = accountRoleService.getUsername();
            if (username == null) throw new UnauthorizedException();

            Account mentorLogged = accountRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException()
            );
            if(mentorLogged.getIdUser()!=tutorials.get(0).getMentor().getIdUser())throw  new MentorNotTutorialThisFresherException();
        }

        results.get(0).setMentor(tutorials.get(0).getMentor());
        results.get(0).setTestPoint(resultRequest.getTestPoint());
        results.get(0).setStatus(true);
        Result resultsNew=resultRepository.save(results.get(0));
        return new ResultResponse(resultsNew);
    }

}
