package com.ShoppingCart.cart.controller;


import com.ShoppingCart.cart.model.Role;
import com.ShoppingCart.cart.model.User;
import com.ShoppingCart.cart.repository.UserRepository;
import com.ShoppingCart.cart.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private final Set<String> blacklist = new HashSet<>();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(username, authentication.getAuthorities());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7); // Elimina "Bearer " din token
        blacklist.add(token);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(Role.valueOf("USER")));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/token/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        if (blacklist.contains(token) || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Token is invalid or expired");
        }
        return ResponseEntity.ok("Token is valid");
    }
}