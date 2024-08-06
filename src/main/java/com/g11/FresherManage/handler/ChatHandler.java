package com.g11.FresherManage.handler;


import com.g11.FresherManage.repository.AccountConservationRepository;
import com.g11.FresherManage.repository.ConservationRepository;
import com.g11.FresherManage.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    public static Map<String,Map<String,WebSocketSession>> sessions = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public static Lock lock = new ReentrantLock();
    public final Map<String,Map<String,WebSocketSession>> topics = new ConcurrentHashMap<>();
    private final ChatService chatService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
       chatService.addSession(session,lock,topics,sessions);
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        executorService.submit(() -> {
            try {
                chatService.handleMessage(session,message,lock,topics,sessions);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        executorService.submit(() -> {
            try {
                chatService.removeSession(session,status,lock,topics,sessions);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
