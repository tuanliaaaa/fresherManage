package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.historyWorking.FresherCenterResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.exception.account.AccountIsLockException;
import com.g11.FresherManage.exception.center.CenterNotFoundException;
import com.g11.FresherManage.exception.workinghistory.FresherIsNotWorking;
import com.g11.FresherManage.exception.workinghistory.FresherIsWorking;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.HistoryWorkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryWorkingServiceImpl implements HistoryWorkingService {
    private final HistoryWorkingRepository historyWorkingRepository;
    private final WorkingRepository workingRepository;
    private final AccountRepository accountRepository;
    @Override
    public FresherCenterResponse addFresherToCenter(Integer centerId, Integer fresherId)
    {
       Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(
               ()-> new FresherNotFoundException()
       );
       if(fresher.getCurentWorking().trim()!="")throw new FresherIsWorking();
       if(fresher.getIs_active().equals("lock")) throw new AccountIsLockException();
       Working center= workingRepository.getCenterByCenterId(centerId).orElseThrow(
                ()-> new CenterNotFoundException()
       );
       //Add current working to account of fresher
       String market=String.valueOf(center.getMarket().getWorkingId())+"*,";
       fresher.setCurentWorking(String.valueOf(center.getWorkingId())+","+market);
       accountRepository.save(fresher);
       HistoryWorking historyWorking= new HistoryWorking(center,fresher);
       historyWorking=historyWorkingRepository.save(historyWorking);
       FresherCenterResponse fresherCenterResponse=new FresherCenterResponse(historyWorking);
       return fresherCenterResponse;
    }
    @Override
    public FresherCenterResponse transferFresherToCenter(Integer fresherId,Integer newCenterId)
    {

        Working centerNew= workingRepository.getCenterByCenterId(newCenterId).orElseThrow(
                ()-> new CenterNotFoundException()
        );
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(
                ()-> new FresherNotFoundException()
        );
        if(fresher.getCurentWorking().trim()=="")throw new FresherIsNotWorking();
        if(fresher.getIs_active().equals("lock")) throw new AccountIsLockException();
        List<HistoryWorking> historyWorking=historyWorkingRepository.findHistoryWorkingByAccountIs_status(fresher);
        if(historyWorking.size()==0) throw  new EmployeeNotWorkinWorkingException();
        historyWorking.get(0).set_status(false);
        historyWorking.get(0).setEnd_at(LocalDate.now());
        historyWorkingRepository.save(historyWorking.get(0));
        String market=String.valueOf(centerNew.getMarket().getWorkingId())+"*,";
        fresher.setCurentWorking(String.valueOf(centerNew.getWorkingId())+","+market);
        accountRepository.save(fresher);
        HistoryWorking historyWorkingNew= new HistoryWorking(centerNew,fresher);
        historyWorkingNew=historyWorkingRepository.save(historyWorkingNew);
        FresherCenterResponse fresherCenterResponse=new FresherCenterResponse(historyWorkingNew);
        return fresherCenterResponse;
    }
}
