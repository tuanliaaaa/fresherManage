package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.point.PointResponse;

import java.security.Principal;

public interface PointService {
    PointResponse findMyPoint(Principal principal);
    PointResponse getPointByFresherId(Principal principal, int fresherId);
}
