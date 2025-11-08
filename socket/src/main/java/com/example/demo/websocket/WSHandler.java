package com.example.demo.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WSHandler extends TextWebSocketHandler {

    private final Map<Long, Map<String, WebSocketSession>> sessionsByUser = new ConcurrentHashMap<>();

    private Long extractUserId(WebSocketSession session) {
        try {
            URI uri = session.getUri();
            if (uri == null || uri.getQuery() == null) return null;

            for (String part : uri.getQuery().split("&")) {
                String[] kv = part.split("=", 2);
                if (kv.length == 2 && kv[0].equals("userId")) {
                    return Long.valueOf(kv[1]);
                }
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = extractUserId(session);
        if(userId == null){
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        sessionsByUser.computeIfAbsent(userId, k->new ConcurrentHashMap<>())
                .put(session.getId(), session);

        System.out.println("WS connected user=" + userId + " / session=" + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = extractUserId(session);
        if(userId != null){
            Map<String,WebSocketSession> map = sessionsByUser.get(userId);
            if(map != null){
                map.remove(session.getId());
                if(map.isEmpty()) sessionsByUser.remove(userId);
            }
        }
        System.out.println("WS closed user=" + userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){
        System.out.println("client msg:" + message.getPayload());
    }

    public void notify(Long userId, String text) throws IOException {
        Map<String,WebSocketSession> map = sessionsByUser.get(userId);
        if(map == null) return;
        for(WebSocketSession s : map.values()){
            if(s.isOpen()) s.sendMessage(new TextMessage(text));
        }
    }

    public void notifyRead(Long userId, Long id) throws IOException {
        Map<String,WebSocketSession> map = sessionsByUser.get(userId);
        if(map == null) return;
        for(WebSocketSession s : map.values()){
            if(s.isOpen()) s.sendMessage(new TextMessage("READ_MESSAGE:"+id));
        }
    }
}
