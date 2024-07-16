package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.service.FresherService;
import com.g11.FresherManage.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/markets")
@Slf4j
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    @GetMapping("/{marketId}")
    public ResponseEntity<?> getMarketByMarketId(@PathVariable("marketId") Integer marketId)  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                marketService.getMarketById(marketId))
                ,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAll()  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        marketService.findAllMarkets())
                ,HttpStatus.OK);
    }
    @GetMapping("/me")
    public ResponseEntity<?> deleteFrdesherByFresherId()  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        marketService.findAllMarkets())
                ,HttpStatus.OK);
    }
}
