package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountConservation;
import com.g11.FresherManage.entity.Conservation;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.handler.ChatHandler;
import com.g11.FresherManage.repository.AccountConservationRepository;
import com.g11.FresherManage.repository.AccountRepository;
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
    private final AccountConservationRepository accountConservationRepository;
    @Override
    public void addSession(WebSocketSession session, Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions) throws Exception {
        session.getAttributes().put("topicsOfSession", new ArrayList<>());
        Account userLogining = accountRepository.findByUsername(session.getAttributes().get("username").toString()).
        orElseThrow(
                () -> new UsernameNotFoundException()
        );
        List<Conservation> conservations=accountConservationRepository.findConservationsByAccount(userLogining);
        for(Conservation conservation:conservations)
        {

            addConservationDetail(session,userLogining,conservation,lock,topics,sessions);
        }
    }
    @Async
    public void addConservationDetail (
            WebSocketSession session,Account userLogining,
            Conservation conservation,
            Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions){

        lock.lock();
        ((List )session.getAttributes().get("topicsOfSession")).add(conservation.getConservationName());
        try {
            if(topics.get(conservation.getConservationName())== null){
//                Map<String,WebSocketSession> sess= new HashMap<>();
                Map<String,WebSocketSession> sessInTopic= new HashMap<>();
//                sess.put(session.getId(),session);
                sessInTopic.put(session.getId(),session);
//                sessions.put(userLogining.getUsername(), sess);
                topics.put(conservation.getConservationName(),sessInTopic);
                log.info("addConservationDetail to topic with id:{}",session.getId());

            }else{
                    topics.get(conservation.getConservationName()).put(session.getId(),session);
//                    sessions.get(userLogining.getUsername()).put(session.getId(),session);
                    log.info("ddConservationDetail to topic with id:{}",session.getId());
            }

        } finally {
            lock.unlock();
        }

    }

    @Override
    public void handleMessage(WebSocketSession session, TextMessage message,Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions){
        log.info("list toiec:{}",session.getAttributes().get("topicsOfSession"));
        log.info("handleMessage topic:{}",message.getPayload());
        Map<String,WebSocketSession> listSocketInTopic=topics.get(message.getPayload());
        log.info("handleMessage listSocketInTopic:{}",listSocketInTopic);
        lock.lock();
        for(Map.Entry<String,WebSocketSession> entry:listSocketInTopic.entrySet())
        {
           sendMessage(session,message,entry.getValue());
        }
        lock.unlock();

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
                log.info("handleMessage session id:{}",session.getAttributes().get("username"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Session is closed: " );
        }
    }
}
