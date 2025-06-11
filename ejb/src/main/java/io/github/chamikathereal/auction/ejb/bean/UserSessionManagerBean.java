package io.github.chamikathereal.auction.ejb.bean;

import io.github.chamikathereal.auction.core.model.User;
import io.github.chamikathereal.auction.ejb.remote.UserSessionManager;
import jakarta.ejb.Remote;
import jakarta.ejb.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Remote(UserSessionManager.class)
public class UserSessionManagerBean implements UserSessionManager {
    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    @Override
    public void createSession(String sessionId, User user) {
        sessions.put(sessionId, user);
    }

    @Override
    public User getUser(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
