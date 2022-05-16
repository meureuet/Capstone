package com.hansungmarket.demo.config;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener {

    // 웹소켓 연결 이벤트
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        Authentication authentication = (Authentication) event.getUser();
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println(principalDetails.getUserId());
    }

    // 구독 이벤트
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
//        MessageHeaders headers = event.getMessage().getHeaders();
//        Object simpDestination = headers.get("simpDestination");
    }

    // 연결 종료 이벤트
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        Authentication authentication = (Authentication) event.getUser();
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println(principalDetails.getUserId());
    }
}
