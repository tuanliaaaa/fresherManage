package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.CenterResponse;

import java.security.Principal;
import java.util.List;

public interface CenterService {
    CenterResponse getCenterByCenterId(Principal principal,Integer centerId);
    List<CenterResponse> findMyCenter(Principal principal);
    List<CenterResponse> findAllCenter(Principal principal,Integer page);
    List<CenterResponse> findAllCenterByMarketID(Principal principal,Integer page);
}
