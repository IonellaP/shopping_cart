package com.ShoppingCart.cart.service;

import  org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();

    public String blacklistToken(String token) {
        blacklistedTokens.add(token);
        return token;
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public void clearBlacklist() {
        blacklistedTokens.clear();
    }
}
