package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.statistic.FresherAmountResponse;
import com.g11.FresherManage.dto.response.statistic.FresherPointResponse;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
import com.g11.FresherManage.repository.ResultRepository;
import com.g11.FresherManage.service.StatistisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StatisticServiceImpl implements StatistisService {
    private final AccountRepository accountRepository;
    private final StatistisService statistisService;
    private final HistoryWorkingRepository historyWorkingRepository;
    private final ResultRepository resultRepository;

    @Override
    public List<FresherPointResponse> findStatisticFresherPoint(List<Double> rankPointList,String typePoint,String workingType, Integer workingId)
    {
        List<FresherPointResponse> fresherPointResponseList = new ArrayList<>();
        if(typePoint.equals("avg"))
        {
            for(int i=0;i<rankPointList.size();i++)
            {
                if(i==0)fresherPointResponseList.add(
                        statistisService.countAccountsWithAvgInRange(0.0
                                ,rankPointList.get(i)));
                else {
                    fresherPointResponseList.add(
                            statistisService.countAccountsWithAvgInRange(
                                    rankPointList.get(i-1)
                                    ,rankPointList.get(i)));
                }
            }
            fresherPointResponseList.add(
                    statistisService.countAccountsWithAvgInRange(
                            rankPointList.get(rankPointList.size()-1)
                            ,10.0));
        } else {
            for(int i=0;i<rankPointList.size();i++)
            {
                if(i==0)fresherPointResponseList.add(countAccountsWithTestInRange(Integer.parseInt(typePoint),0.0,rankPointList.get(0)));
                else{
                    fresherPointResponseList.add(countAccountsWithTestInRange(Integer.parseInt(typePoint),rankPointList.get(i-1),rankPointList.get(i)));
                }
            }
            fresherPointResponseList.add(countAccountsWithTestInRange(Integer.parseInt(typePoint),rankPointList.get(rankPointList.size()-1),10.0));
        }
        return fresherPointResponseList;
    }

    @Override
    public FresherPointResponse countAccountsWithAvgInRange(Double fromPoint, Double toPoint)
    {
        return new FresherPointResponse(toPoint,resultRepository.countAccountsWithAvgInRange(fromPoint,toPoint));
    }
    @Override
    public FresherPointResponse countAccountsWithTestInRange(Integer numberTest,Double fromPoint, Double toPoint)
    {
        return new FresherPointResponse(toPoint,resultRepository.countAccountsWithTestInRange(numberTest,fromPoint,toPoint));
    }
    @Override
    public List<FresherAmountResponse> findStatisticFresherAmount(LocalDate startDate, LocalDate endDate, String workingType, Integer workingId) {
        List<FresherAmountResponse> fresherAmountResponses = new ArrayList<>();
        List<LocalDate> dates = getDatesBetween(startDate, endDate);

        if (workingType == null) {
            for (LocalDate date : dates) {
                fresherAmountResponses.add(statistisService.getAmountCountFresherToDay(date));
            }
        } else if (workingType.equals("CENTER") && workingId!=null) {
            for (LocalDate date : dates) {
                fresherAmountResponses.add(statistisService.getAmountCountFresherByCenterToDay(date,workingId));
            }
        }else if (workingType.equals("MARKET") && workingId!=null) {
            for (LocalDate date : dates) {
                fresherAmountResponses.add(statistisService.getAmountCountFresherByMarketToDay(date,workingId));

            }
        }
        return fresherAmountResponses;
    }
    @Cacheable(value = "fresherAmountCache",key = "#daySearch.toString()")
    public FresherAmountResponse getAmountCountFresherToDay(LocalDate daySearch) {
        return new FresherAmountResponse(daySearch,accountRepository.countFreshersActiveOnDate(daySearch));
    }
    @Override
    @Cacheable(value = "fresherAmountCacheByCenter",key = "#daySearch.toString()")
    public FresherAmountResponse getAmountCountFresherByCenterToDay(LocalDate daySearch,Integer centerId) {
        return new FresherAmountResponse(daySearch,historyWorkingRepository.countFreshersActiveinCenterOnDate(daySearch,centerId));
    }
    @Override
    @Cacheable(value = "fresherAmountCacheByMarket",key = "#daySearch.toString()")
    public FresherAmountResponse getAmountCountFresherByMarketToDay(LocalDate daySearch,Integer centerId) {
        return new FresherAmountResponse(daySearch,historyWorkingRepository.countFreshersActiveinMarketOnDate(daySearch,centerId));
    }


    private static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
    }
}
