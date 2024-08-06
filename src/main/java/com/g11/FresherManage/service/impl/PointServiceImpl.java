package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.request.ResultRequest;
import com.g11.FresherManage.dto.response.Result.PointResponse;
import com.g11.FresherManage.dto.response.Result.ResultResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Result;
import com.g11.FresherManage.entity.Tutorial;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.NotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.result.ResultNotFoundException;
import com.g11.FresherManage.exception.test.TestNotFoundException;
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
        Object[] resulsts= resultRepository.getMaxScoresAndAverageForAccount(userLogining.getIdUser());
        if(resulsts.length==0){
            return new PointResponse();
        }
        PointResponse pointResponse = new PointResponse();
        Object[] row = (Object[]) resulsts[0];
        if(row[0]!=null)pointResponse.setLessson1(Double.parseDouble(row[0].toString()));
        if(row[1]!=null) pointResponse.setLessson2(Double.parseDouble(row[1].toString()));
        if(row[2]!=null) pointResponse.setLessson3(Double.parseDouble(row[2].toString()));
        if(row[3]!=null)pointResponse.setAverage(Double.parseDouble(row[3].toString()));

        return pointResponse;
    }

    @Override
    public PointResponse getPointByFresherId(Integer fresherId)
    {
        //Get username of account logging
        String username = accountRoleService.getUsername();
        if(username==null) throw new UnauthorizedException();

        //Get role of account logging
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        if(!roleList.contains("ROLE_ADMIN")&& roleList.contains("ROLE_MARKETDIRECTOR"))
        {
            // Check if the market director is working at the market where the fresher is working.
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
        } else if (!roleList.contains("ROLE_ADMIN")) {
            //Check if the center director or mentor is working at the center where the fresher is working.
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
                    .filter(id -> !id.endsWith("*"))
                    .collect(Collectors.toList());
            int dem=0;
            for(String workingId : workingIdsLoging)
            {
                if(curentWorkingLogining.contains(workingId))
                {
                    dem++;
                    break;
                }
            }
            if(dem==0) throw new EmployeeNotWorkinWorkingException();
        }
        // Get point By fresherId
        Object[] resulsts= resultRepository.getMaxScoresAndAverageForAccount(fresherId);
        if(resulsts.length==0){
            return new PointResponse();
        }
        PointResponse pointResponse = new PointResponse();
        Object[] row = (Object[]) resulsts[0];
        if(row[0]!=null)pointResponse.setLessson1(Double.parseDouble(row[0].toString()));
        if(row[1]!=null) pointResponse.setLessson2(Double.parseDouble(row[1].toString()));
        if(row[2]!=null) pointResponse.setLessson3(Double.parseDouble(row[2].toString()));
        if(row[3]!=null)pointResponse.setAverage(Double.parseDouble(row[3].toString()));

        return pointResponse;
    }
    @Override
    public ResultResponse addPointByFresherId(Integer fresherId, ResultRequest resultRequest)
    {
        Result result=resultRepository.findById(resultRequest.getId()).orElseThrow(
                ()->new TestNotFoundException()
        );
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

        result.setMentor(tutorials.get(0).getMentor());
        result.setTestPoint(resultRequest.getTestPoint());
        result.setStatus(true);
        Result resultsNew=resultRepository.save(result);
        return new ResultResponse(resultsNew);
    }

}
