package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.dto.response.statistic.FresherAmountResponse;
import com.g11.FresherManage.dto.response.statistic.FresherPointResponse;
import com.g11.FresherManage.repository.ResultRepository;

import java.time.LocalDate;
import java.util.List;

public interface StatistisService
{
    List<FresherAmountResponse> findStatisticFresherAmount (LocalDate startDate,LocalDate endDate,String workingType,Integer workingId);
    FresherAmountResponse getAmountCountFresherToDay(LocalDate daySearch);
    FresherAmountResponse getAmountCountFresherByCenterToDay(LocalDate daySearch,Integer centerId);
    FresherAmountResponse getAmountCountFresherByMarketToDay(LocalDate daySearch,Integer centerId);
    List<FresherPointResponse> findStatisticFresherPoint(List<Double> rankPointList,String typePoint);
    FresherPointResponse countAccountsWithAvgInRange(Double fromPoint, Double toPoint);
    FresherPointResponse countAccountsWithTestInRange(Integer numberTest,Double fromPoint, Double toPoint);

}
