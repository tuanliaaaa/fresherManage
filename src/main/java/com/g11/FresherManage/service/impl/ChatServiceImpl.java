package com.g11.FresherManage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g11.FresherManage.dto.request.message.MessageRequest;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountConservation;
import com.g11.FresherManage.entity.Conservation;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.conservation.ConservationNotFoundException;
import com.g11.FresherManage.handler.ChatHandler;
import com.g11.FresherManage.repository.AccountConservationRepository;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ConservationRepository;
import com.g11.FresherManage.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConservationRepository conservationRepository;
    private final AccountConservationRepository accountConservationRepository;
    @Override
    public void addSession(WebSocketSession session, Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions) throws Exception {
        log.info("add session: {}", session.getId());
        session.getAttributes().put("topicsOfSession", new ArrayList<>());
        Account userLogining = accountRepository.findByUsername(session.getAttributes().get("username").toString()).
        orElseThrow(
                () -> new UsernameNotFoundException()
        );
        List<Conservation> conservations=accountConservationRepository.findConservationsByAccount_Username(session.getAttributes().get("username").toString());
        for(Conservation conservation:conservations)
        {

            addConservationDetail(session,userLogining,conservation,lock,topics,sessions);
        }
    }
    @Async
    public void addConservationDetail (
            WebSocketSession session,Account userLogining,
            Conservation conservation,
            Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions)
    {

        lock.lock();
        ((List )session.getAttributes().get("topicsOfSession")).add(conservation.getConservationName());
        try {
            if(topics.get(conservation.getConservationName())== null){
                Map<String,WebSocketSession> sessInTopic= new HashMap<>();
                sessInTopic.put(session.getId(),session);
                topics.put(conservation.getConservationName(),sessInTopic);
            }else{
                    topics.get(conservation.getConservationName()).put(session.getId(),session);
            }

        } finally {
            lock.unlock();
        }

    }

    @Override
    public void handleMessage(WebSocketSession session, TextMessage message,Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions) throws IOException {
        try {
            MessageRequest msg = objectMapper.readValue(message.getPayload(), MessageRequest.class);
            log.info("handleMessage topic:{}",msg);
            Conservation conservation =conservationRepository.findById(msg.getConservationId()).orElseThrow(
                    ()-> new ConservationNotFoundException()
            );
            Map<String,WebSocketSession> listSocketInTopic=topics.get(conservation.getConservationName());
            lock.lock();
            for(Map.Entry<String,WebSocketSession> entry:listSocketInTopic.entrySet())
            {
                sendMessage(session,message,entry.getValue());
            }
            lock.unlock();
        }
        catch (Exception e) {
            session.sendMessage(new TextMessage("Lỗi Định dạng"));
        }
    }

    @Override
    public void removeSession(WebSocketSession session, CloseStatus status, Lock lock, Map<String, Map<String, WebSocketSession>> topics, Map<String, Map<String, WebSocketSession>> sessions) throws Exception {
        session.close();
    }
    @Async
    public void sendMessage(WebSocketSession session, TextMessage message,WebSocketSession ed)
    {
        if (ed.isOpen()) {
            try {
                ed.sendMessage(new TextMessage(message.getPayload()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Session is closed: " );
        }
    }
}
