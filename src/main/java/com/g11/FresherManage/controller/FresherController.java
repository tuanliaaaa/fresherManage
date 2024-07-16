package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.dto.request.fresher.FresherRequest;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.service.FresherService;
import jakarta.validation.Valid;
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

    //------------------ Get List Fresher By Working ID -------------------------
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/working/{workingId}")
    public ResponseEntity<?> findFresherByWorkingId(Principal principal,@PathVariable Integer workingId,@RequestParam(defaultValue = "0") Integer page)
    {
        return new ResponseEntity<>(
                ResponseGeneral.of(200,"success",
                        fresherService.findFresherByWorkingId(principal,workingId, page)),
                HttpStatus.OK);
    }





    //------------------- get All Fresher ----------------------------------------
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','MARKETDIRECTOR','CENTERDIRECTOR')")
    @GetMapping("/admin/freshers")
    public ResponseEntity<?> findAllFreshers(Principal principal,@RequestParam(defaultValue = "0") Integer page)  {
        return new ResponseEntity<>(ResponseGeneral.of(200,"success",fresherService.findAllFreshers(principal,page)), HttpStatus.OK);
    }

    //------------------- Delete Fresher By Fresher ID----------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{fresherId}")
    public ResponseEntity<?> deleteFrdesherByFresherId(@PathVariable("fresherId") Integer fresherId)  {
        fresherService.deleteFrdesherByFresherId(fresherId) ;
        return new ResponseEntity<>(
                ResponseGeneral.ofSuccess("Delete Fresher Success"),HttpStatus.NO_CONTENT);
    }


    //------------------- Create Fresher-----------------------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createFresher(@Valid @RequestBody FresherRequest fresherRequest)  {
        return new ResponseEntity<>(
                ResponseGeneral.ofCreated("success",
                        fresherService.createFresher(fresherRequest)),HttpStatus.CREATED);
    }


}
