package com.ShoppingCart.cart.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SessionService {
    private static SessionService instance;
    private Map<Long, CartService> sessions;
    private Random random;

    private SessionService() {
        sessions = new HashMap<>();
        random = new Random();
    }

    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }
    public Long createNewSession(){
        Long sessionId = random.nextLong();
        sessions.put(sessionId, new CartService());
            return sessionId;
    }

    public CartService getCartBySession(Long sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(Long sessionId) {
        sessions.remove(sessionId);
    }

}
