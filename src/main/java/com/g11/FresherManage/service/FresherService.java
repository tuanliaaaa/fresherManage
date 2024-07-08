package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.FresherResponse;
import com.g11.FresherManage.entity.Fresher;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FresherService {
    List<FresherResponse> findAllFreshers(int offset, int limit) ;
    FresherResponse getFresherByFresherId(Integer fresherId);
    Void deleteFrdesherByFresherId(Integer fresherId);
    FresherResponse findByUsername(String username);
}
