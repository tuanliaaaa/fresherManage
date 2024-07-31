package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.statistic.FresherAmountResponse;

import java.time.LocalDate;
import java.util.List;

public interface StatistisService {
    List<FresherAmountResponse> findStatisticFresherAmount (LocalDate startDate,LocalDate endDate,String workingType,Integer workingId);
    FresherAmountResponse getAmountCountFresherToDay(LocalDate daySearch);
    FresherAmountResponse getAmountCountFresherByCenterToDay(LocalDate daySearch,Integer centerId);
    FresherAmountResponse getAmountCountFresherByMarketToDay(LocalDate daySearch,Integer centerId);

}
