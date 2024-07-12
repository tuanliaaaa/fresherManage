package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.fresher.FresherResponse;

import java.security.Principal;
import java.util.List;

public interface FresherService {
    List<FresherResponse> findAllFreshers(int offset, int limit) ;
    FresherResponse getFresherByFresherId(Principal principal,Integer fresherId);
    Void deleteFrdesherByFresherId(Integer fresherId);
    FresherResponse getMyFresherInfo(Principal principal);
    List<FresherResponse> getFreshersForAnotherAdmin(Principal principal,Integer offset,Integer limit);
    List<FresherResponse> findFresherByCenterId(Principal principal,Integer centerId);
}
