package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.ResultRequest;
import com.g11.FresherManage.dto.response.Result.PointResponse;
import com.g11.FresherManage.dto.response.Result.ResultResponse;

public interface PointService {
    PointResponse findPointByUsername(String username);
    PointResponse getPointByFresherId(Integer fresherId);
    ResultResponse addPointByFresherId(Integer fresherId, ResultRequest resultRequest);
}
