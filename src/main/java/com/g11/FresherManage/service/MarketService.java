package com.g11.FresherManage.service;

import com.g11.FresherManage.dto.response.MarketResponse;

import java.util.List;

public interface MarketService {
    MarketResponse getMarketById(Integer marketId);
    List<MarketResponse> findAllMarkets();
}
