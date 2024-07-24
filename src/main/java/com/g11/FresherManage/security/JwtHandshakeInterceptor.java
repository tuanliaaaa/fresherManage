package com.g11.FresherManage.security;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
@Slf4j
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {


    private final  JwtUtilities jwtUtilities;



    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7, bearerToken.length());  // The part after "Bearer "
            if (token != null && jwtUtilities.validateToken(token)) {
                String username = jwtUtilities.extractUsername(token);
                attributes.put("username", username);
                attributes.put("token", token);
                log.info("(true) request:{}", token);

                return true;
            }
            log.info("(false) request:{}", token);
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Không cần xử lý sau handshake trong trường hợp này
    }


}
