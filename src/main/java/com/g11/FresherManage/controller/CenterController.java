package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.service.CenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/centers")
@Slf4j
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyCenterInfor(Principal principal)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                centerService.findMyCenter(principal)), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{centerId}")
    public ResponseEntity<?> getCenterByCenterId(Principal principal,
                                 @PathVariable("centerId") Integer centerId)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                centerService.getCenterByCenterId(principal,centerId)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> findAllCenter(Principal principal,
                                          @RequestParam(required = false) Integer page)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.findAllCenter(principal,page)), HttpStatus.OK);
    }
    @GetMapping("/market/{marketId}")
    public ResponseEntity<?> findAllCenterByMarketID(Principal principal, @PathVariable("marketId") Integer marketId)  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        centerService.findAllCenterByMarketID(principal,marketId)), HttpStatus.OK);
    }
}
