//package com.g11.FresherManage.controller;
//
//import com.g11.FresherManage.dto.ResponseGeneral;
//import com.g11.FresherManage.service.MarketService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/points")
//@Slf4j
//@RequiredArgsConstructor
//public class PointController {
//    private final MarketService marketService;
//
//    @GetMapping("/{fresherID}")
//    public ResponseEntity<?> getMarketByMarketId(@PathVariable("fresherID") Integer fresherID)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        marketService.getMarketById(fresherID))
//                , HttpStatus.OK);
//    }
//
//
//    @GetMapping("/me")
//    public ResponseEntity<?> deleteFrdesherByFresherId()  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        marketService.findAllMarkets())
//                ,HttpStatus.OK);
//    }
//}
