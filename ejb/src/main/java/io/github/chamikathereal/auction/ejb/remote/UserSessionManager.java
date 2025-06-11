package io.github.chamikathereal.auction.ejb.remote;

import io.github.chamikathereal.auction.core.model.User;
import jakarta.ejb.Remote;

@Remote
public interface UserSessionManager {
    void createSession(String sessionId, User user);
    User getUser(String sessionId);
    void invalidateSession(String sessionId);
}
