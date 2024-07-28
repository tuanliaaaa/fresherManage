package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.request.center.CenterRequest;
import com.g11.FresherManage.dto.request.center.CenterUpdateRequest;
import com.g11.FresherManage.dto.request.market.MarketRequest;
import com.g11.FresherManage.dto.request.market.MarketUpdateRequest;
import com.g11.FresherManage.dto.response.center.CenterResponse;
import com.g11.FresherManage.dto.response.market.MarketResponse;

import java.security.Principal;
import java.util.List;

public interface MarketService {
    List<MarketResponse> findMarketOfUsername(String username);
    MarketResponse getMarketByMarketId( Integer marketId);
    List<MarketResponse> findAllMarket(Integer page);
    void deleteMarketByMarketId(Integer marketId);
    MarketResponse updateMarketByMarketId(Integer marketId, MarketUpdateRequest marketUpdateRequest);
    MarketResponse createMarket(MarketRequest marketRequest);
}
