package com.g11.FresherManage.service;

import com.g11.FresherManage.entity.Conservation;

import java.util.List;

public interface ConservationService {
    List<Conservation> findAllMyConservations(String username);
}
