package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.dto.response.center.CenterResponse;

import java.security.Principal;
import java.util.List;

public interface CenterService {
    CenterResponse getCenterByCenterId(Integer centerId);
    List<CenterResponse> findCenterByUsername(String username);
    List<CenterResponse> findAllCenter(Integer page);
    List<CenterResponse> findAllCenterByMarketID(Principal principal,Integer page);
    void deleteCenterByCenterId(Integer centerId);
    CenterResponse updateCenterByCenterId(Integer centerId, CenterUpdateRequest centerUpdateRequest);
    CenterResponse createCenter(CenterRequest centerRequest);
}
