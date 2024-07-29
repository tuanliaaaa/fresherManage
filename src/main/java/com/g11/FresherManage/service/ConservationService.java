package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.conservation.ConservationResponse;
import com.g11.FresherManage.entity.Conservation;

import java.util.List;

public interface ConservationService {
    List<ConservationResponse> findAllConservationsByUsername(String username);
}
