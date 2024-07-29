package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.historyWorking.FresherCenterResponse;
import com.g11.FresherManage.entity.HistoryWorking;

public interface HistoryWorkingService {
    FresherCenterResponse addFresherToCenter(Integer centerId, Integer fresherId);
    FresherCenterResponse transferFresherToCenter( Integer fresherId,Integer newCenterId);
}
