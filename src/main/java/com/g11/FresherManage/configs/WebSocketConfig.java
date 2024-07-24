package com.g11.FresherManage.configs;

import com.g11.FresherManage.handler.ChatHandler;
import com.g11.FresherManage.security.JwtHandshakeInterceptor;
import com.g11.FresherManage.security.JwtUtilities;
import com.g11.FresherManage.service.ChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatService chatService;
    private final JwtUtilities jwtUtilities;

    public WebSocketConfig(ChatService chatService, JwtUtilities jwtUtilities) {
        this.chatService = chatService;
        this.jwtUtilities = jwtUtilities;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler( chatHandler(), "/chat") .addInterceptors( jwtHandshakeInterceptor());
    }
    @Bean
    public ChatHandler chatHandler() {
        return new ChatHandler(chatService);
    }
    @Bean
    public JwtHandshakeInterceptor jwtHandshakeInterceptor() {
        return new JwtHandshakeInterceptor(jwtUtilities);
    }
}
