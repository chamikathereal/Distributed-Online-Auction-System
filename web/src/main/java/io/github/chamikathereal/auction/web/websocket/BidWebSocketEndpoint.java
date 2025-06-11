package io.github.chamikathereal.auction.web.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/bidsocket")
public class BidWebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        WebSocketBroadcaster.addSession(session);
        System.out.println("[WS][ENDPOINT] Session opened: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        WebSocketBroadcaster.removeSession(session);
        System.out.println("[WS][ENDPOINT] Session closed: " + session.getId());
    }
}