package com.g11.FresherManage.controller;

import com.g11.FresherManage.dto.ResponseGeneral;
import com.g11.FresherManage.service.ChatService;
import com.g11.FresherManage.service.ConservationService;
import com.g11.FresherManage.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/conservations")
@Slf4j
@RequiredArgsConstructor
public class ConservationController {
    private final ConservationService conservationService;
    private final MessageService messageService;

    @PreAuthorize("authenticated()")
    @GetMapping("/me")
    public ResponseEntity<?> findAllMyConservations(
            Principal principal)
    {
        log.info("findAllMyConservations :{}", principal.getName());
        return new ResponseEntity<>(ResponseGeneral.of(200,
                "success",
                conservationService.findAllMyConservations(principal.getName())), HttpStatus.OK);
    }
    @PreAuthorize("authenticated()")
    @GetMapping("/chat/{conservationId}")
    public ResponseEntity<?> findChatByConservationId(
            Principal principal,
            @Param("conservationId") Integer conservationId,
            @RequestParam(defaultValue = "0") Integer page)
    {
        log.info("find Chat By Conservation Id :{} of User: {}", conservationId,principal.getName());
        return new ResponseEntity<>(ResponseGeneral.of(200,
                "success",
                messageService.findMessageByConservationId(principal.getName(),conservationId,page)), HttpStatus.OK);
    }
}
