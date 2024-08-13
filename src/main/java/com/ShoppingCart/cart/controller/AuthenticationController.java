package com.ShoppingCart.cart.controller;

import com.ShoppingCart.cart.entity.CustomUserDetailsService;
import com.ShoppingCart.cart.model.LoginRequest;
import com.ShoppingCart.cart.model.LoginResponse;
import com.ShoppingCart.cart.service.AuthService;
import com.ShoppingCart.cart.service.JwtService;
import com.ShoppingCart.cart.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthenticationController(AuthService authService, TokenBlacklistService tokenBlacklistService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = tokenBlacklistService.blacklistToken(String.valueOf(request));
        if (token != null) {
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("No token found");
    }

    @PostMapping("/admin/block-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> blockUser(@RequestParam String userEmail) {
        UserDetails userDetails = CustomUserDetailsService.loadUserByUsername(userEmail);
        if (userDetails != null) {
            String token = JwtService.generateToken(userDetails);
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("User blocked successfully");
        }
        return ResponseEntity.badRequest().body("User not found");
    }
}
