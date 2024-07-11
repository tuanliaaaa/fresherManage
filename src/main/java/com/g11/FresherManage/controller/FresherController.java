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
//
//    //------------------- Get Infor of Fresher Loging------------------------------
//    @PreAuthorize("hasRole('Fresher')")
//    @GetMapping("/me")
//    public ResponseEntity<?> getMyFresherInfo(Principal principal) {
//        String username = principal.getName();
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                        fresherService.getMyFresherInfo(principal)),
//                        HttpStatus.OK);
//    }
//
//    //------------------- Get Infor of Fresher By Mentor --------------------------
//    @GetMapping("/mentor/freshers")
//    public ResponseEntity<List<?>> getFreshersForMentor(Principal principal,
//        @RequestParam(required = false) String a,
//        @RequestParam(required = false) String b)
//    {
//        //Long mentorId = Long.valueOf(principal.getName());
//        List<FresherResponse> freshers = fresherService.getFreshersForMentor(a,b);
//        return ResponseEntity.ok(freshers);
//    }
//
//
//    @GetMapping
//    public ResponseEntity<?> findAllFreshers(@RequestParam(defaultValue = "0") Integer page)  {
//        return new ResponseEntity<>(ResponseGeneral.of(200,"success",fresherService.findAllFreshers(page*10, (page+1)*10)), HttpStatus.OK);
//    }
//    @GetMapping("/{fresherId}")
//    public ResponseEntity<?> getFresherByFresherId(@PathVariable("fresherId") Integer fresherId)  {
//        return new ResponseEntity<>(
//                ResponseGeneral.of(200,"success",
//                fresherService.getFresherByFresherId(fresherId)),HttpStatus.OK);
//    }
//    @DeleteMapping("/{fresherId}")
//    public ResponseEntity<?> deleteFrdesherByFresherId(@PathVariable("fresherId") Integer fresherId)  {
//        fresherService.deleteFrdesherByFresherId(fresherId) ;
//        return new ResponseEntity<>(
//                ResponseGeneral.ofSuccess("Delete Fresher Success"),HttpStatus.NO_CONTENT);
//    }
//

}
