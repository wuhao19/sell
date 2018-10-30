package com.wuhao.sell.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        log.info("建立连接，连接数：{}",webSockets.size());
    }
    @OnClose
    public void onClose(){
        webSockets.remove(this);
        log.info("关闭连接，连接数：{}",webSockets.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("收到webSocket消息：{}",message);
    }
    public void sendMessage(String message){
        for (WebSocket webSocket:webSockets){
            try {
                log.info("webSocket广播消息：{}",message);
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
