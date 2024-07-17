package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.service.MarketService;
import com.g11.FresherManage.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/v1/points")
@Slf4j
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("/{fresherID}")
    public ResponseEntity<?> getPointByFresherId(Principal principal,@PathVariable("fresherID") Integer fresherID)  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        pointService.getPointByFresherId(principal,fresherID))
                , HttpStatus.OK);
    }

    @PreAuthorize("hasRole('FRESHER')")
    @GetMapping("/me")
    public ResponseEntity<?> findMyPoint(Principal principal)  {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        pointService.findMyPoint(principal))
                ,HttpStatus.OK);
    }
}
