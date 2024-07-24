package com.g11.FresherManage.service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public interface ChatService {
    void addSession(WebSocketSession session, Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions) throws Exception;
    void handleMessage(WebSocketSession session, TextMessage message,Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions) throws IOException, InterruptedException;
    void removeSession(WebSocketSession session, CloseStatus status,Lock lock,Map<String, Map<String,WebSocketSession>> topics,Map<String,Map<String,WebSocketSession>> sessions) throws Exception;
}
