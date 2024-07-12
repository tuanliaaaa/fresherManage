package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.service.FresherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/freshers")
@Slf4j
@RequiredArgsConstructor
public class FresherController {
    private final FresherService fresherService;

    //------------------- Get Infor of Fresher Loging------------------------------
    @PreAuthorize("hasRole('FRESHER')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyFresherInfo(Principal principal)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.getMyFresherInfo(principal)),
                        HttpStatus.OK);
    }

    //------------------ Get Detail Fresher By Fresher ID -------------------------
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/{fresherID}")
    public ResponseEntity<?> getFresherByFresherId(Principal principal,@PathVariable Integer fresherID)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.getFresherByFresherId(principal,fresherID)),
                HttpStatus.OK);
    }

    //------------------ Get List Fresher By Center ID -------------------------
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/center/{centerID}")
    public ResponseEntity<?> findFresherByCenterId(Principal principal,@PathVariable Integer centerID)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.findFresherByCenterId(principal,centerID)),
                HttpStatus.OK);
    }

    //------------------ Get List Fresher By Market ID -------------------------
    @PreAuthorize("hasAnyRole('ADMIN','MARKETDIRECTOR')")
    @GetMapping("/market/{marketID}")
    public ResponseEntity<?> findFresherByMarketId(Principal principal,@PathVariable Integer marketID)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.findFresherByCenterId(principal,marketID)),
                HttpStatus.OK);
    }


    //------------------- Get All Fresher by Role Another Admin--------------------
    @PreAuthorize("hasAnyRole('MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping
    public ResponseEntity<List<?>> getFreshersForAnotherAdmin(Principal principal,
        @RequestParam(defaultValue = "0") Integer page)
    {
        List<FresherResponse> freshers = fresherService.getFreshersForAnotherAdmin(principal,page*10,(page+1)*10);
        return ResponseEntity.ok(freshers);
    }


    //------------------- get All Fresher ----------------------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/freshers")
    public ResponseEntity<?> findAllFreshers(@RequestParam(defaultValue = "0") Integer page)  {
        return new ResponseEntity<>(ResponseGeneral.of(200,"success",fresherService.findAllFreshers(page*10, (page+1)*10)), HttpStatus.OK);
    }

    //------------------- Delete Fresher By Fresher ID----------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{fresherId}")
    public ResponseEntity<?> deleteFrdesherByFresherId(@PathVariable("fresherId") Integer fresherId)  {
        fresherService.deleteFrdesherByFresherId(fresherId) ;
        return new ResponseEntity<>(
                ResponseGeneral.ofSuccess("Delete Fresher Success"),HttpStatus.NO_CONTENT);
    }


}
