//package com.g11.FresherManage.controller;
//
//import com.g11.FresherManage.dto.ResponseGeneral;
//import com.g11.FresherManage.service.CenterService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/api/v1/centers")
//@Slf4j
//@RequiredArgsConstructor
//public class CenterController {
//    private final CenterService centerService;
//    @GetMapping("/me")
//    public ResponseEntity<?> getMyCenterInfor(Principal principal)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                centerService.findMyCenter(principal)), HttpStatus.OK);
//    }
//    @GetMapping("/{centerId}")
//    public ResponseEntity<?> getCenterByCenterId(@PathVariable("centerId") Integer centerId)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                centerService.getCenterByCenterId(centerId)), HttpStatus.OK);
//    }
//    @GetMapping
//    public ResponseEntity<?> getAllCenter(Principal principal)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        centerService.findMyCenter(principal)), HttpStatus.OK);
//    }
//    @GetMapping
//    public ResponseEntity<?> getAllCenterByMarketID(Principal principal)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        centerService.findMyCenter(principal)), HttpStatus.OK);
//    }
//}
