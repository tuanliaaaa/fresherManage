package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.MarketResponse;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketServiceImpl implements MarketService {
    private final WorkingRepository workingRepository;
    @Override
    public MarketResponse getMarketById(Integer marketId){
        MarketResponse marketResponse = new MarketResponse();
        return marketResponse;
    }
    @Override
    public List<MarketResponse> findAllMarkets(){
        List<MarketResponse> marketResponseList = new ArrayList<>();
        List<Working> workingList = workingRepository.findAll();
        return marketResponseList;
    }

}
