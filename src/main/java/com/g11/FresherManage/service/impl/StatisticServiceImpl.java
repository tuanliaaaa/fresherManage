package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.statistic.FresherAmountResponse;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
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
    @Override
//    @Cacheable(value = "fresherAmountCache", key = "{#startDate, #endDate, #workingType, #workingId}")

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
