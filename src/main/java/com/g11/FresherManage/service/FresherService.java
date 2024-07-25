package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.fresher.FresherRequest;
import com.g11.FresherManage.dto.request.fresher.FresherUpdateRequest;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;

import java.security.Principal;
import java.util.List;

public interface FresherService {
    List<FresherResponse> findAllFreshers(Principal principal,Integer page) ;
    FresherResponse getFresherByFresherId(Principal principal,Integer fresherId);
    Void deleteFrdesherByFresherId(Integer fresherId);
    FresherResponse getMyFresherInfo(Principal principal);
    List<FresherResponse> getFreshersForAnotherAdmin(Principal principal,Integer offset,Integer limit);
    List<FresherResponse> findFresherByWorkingId(Principal principal,Integer workingId,Integer page);
    FresherResponse createFresher(FresherRequest fresherRequest);
    FresherResponse updateFresher(Integer fresherId, FresherUpdateRequest fresherUpdateRequest);
    List<FresherResponse> searchFreshers(String firstName,String lastName,String phone,String email);
}
