package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.CenterResponse;
import com.g11.FresherManage.dto.response.MarketResponse;

import java.security.Principal;
import java.util.List;

public interface MarketService {
    List<MarketResponse> findMyMarket(Principal principal);
    MarketResponse getMarketByMarketId(Principal principal, Integer marketId);
    List<MarketResponse> findAllMarket(Principal principal,Integer page);

}
