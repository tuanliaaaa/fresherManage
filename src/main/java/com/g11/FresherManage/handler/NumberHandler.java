package com.g11.FresherManage.handler;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumberHandler extends TextWebSocketHandler {
    private static Map<String,WebSocketSession> sessions = new HashMap<String,WebSocketSession>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Lock lock = new ReentrantLock();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        lock.lock(); // Bắt đầu lock
        try {
            sessions.put(session.getId(), session);
            System.out.println("Session added. Total sessions: " + sessions.size());
        } finally {
            lock.unlock(); // Đảm bảo unlock ngay cả khi có lỗi xảy ra
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        executorService.submit(() -> {
            if(message.getPayload().equals("toi la 1")) for (WebSocketSession session2 : sessions.values())System.out.println(session2.getAttributes().get("token"));;
            for (WebSocketSession session2 : sessions.values()) {
                if (session2.isOpen()) {
                    try {
                        session2.sendMessage(new TextMessage(message.getPayload()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Session is closed: " + session2.getId());
                }
            }
        });
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        lock.lock(); // Bắt đầu lock
        try {
            sessions.remove(session.getId());
            System.out.println("Session removed. Total sessions: " + sessions.size());
        } finally {
            lock.unlock(); // Đảm bảo unlock ngay cả khi có lỗi xảy ra
        }
    }
}
