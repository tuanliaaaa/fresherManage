package com.g11.FresherManage.controller;

import com.g11.FresherManage.service.FresherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Api/V1/Fresher")
@Slf4j
@RequiredArgsConstructor
public class FresherController {
    private final FresherService fresherService;
//    @GetMapping("/freshers")
//    public List<Fresher> getFreshers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        return fresherService.findAll(page,size);
//    }
}
