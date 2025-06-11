package io.github.chamikathereal.auction.web.websocket;

import jakarta.websocket.Session;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketBroadcaster {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    public static void addSession(Session session) {
        sessions.add(session);
        System.out.println("[WS][SESSION] Added: " + session.getId() + " (Total: " + sessions.size() + ")");
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
        System.out.println("[WS][SESSION] Removed: " + session.getId() + " (Total: " + sessions.size() + ")");
    }

    public static void broadcast(String message) {
        System.out.println("[WS][BROADCAST] Broadcasting to " + sessions.size() + " sessions.");
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.err.println("[WS][ERROR] Error sending to session [" + session.getId() + "]: " + e.getMessage());
                    removeSession(session);
                }
            }
        }
    }
}

