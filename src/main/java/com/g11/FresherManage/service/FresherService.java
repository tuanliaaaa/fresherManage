package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.fresher.FresherRequest;
import com.g11.FresherManage.dto.request.fresher.FresherUpdateRequest;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;

import java.security.Principal;
import java.util.List;

public interface FresherService {
    List<FresherResponse> findAllFreshers(Integer page) ;
    FresherResponse getFresherByFresherId(Integer fresherId);
    Void deleteFrdesherByFresherId(Integer fresherId);
    FresherResponse getFresherByUsername(String username);
    List<FresherResponse> findFresherByCenterId(Integer centerId,Integer page);
    FresherResponse createFresher(FresherRequest fresherRequest);
    FresherResponse updateFresher(Integer fresherId, FresherUpdateRequest fresherUpdateRequest);
    List<FresherResponse> searchFreshers(String firstName,String lastName,String phone,String email);
    List<FresherResponse> findFresherByMarketId(Integer marketId,Integer page);

}
