package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.service.CenterService;
import com.g11.FresherManage.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/markets")
@Slf4j
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;
    private final CenterService centerService;
    @PreAuthorize("hasAnyRole('ADMIN','MARKETDIRECTOR')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyMarketInfor(Principal principal)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        marketService.findMyMarket(principal)), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{centerId}")
    public ResponseEntity<?> getCenterByCenterId(
         @PathVariable("centerId") Integer centerId)
    {
        log.info("get center by id: {}", centerId);
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.getCenterByCenterId(centerId)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> findAllCenter(
       @RequestParam(required = false) Integer page)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.findAllCenter(page)), HttpStatus.OK);
    }
    @GetMapping("/market/{marketId}")
    public ResponseEntity<?> findAllCenterByMarketID(Principal principal, @PathVariable("marketId") Integer marketId)  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.findAllCenterByMarketID(principal,marketId)), HttpStatus.OK);
    }
}
