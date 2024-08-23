package com.ShoppingCart.cart.controller;

import com.ShoppingCart.cart.model.User;
import com.ShoppingCart.cart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserRepository userRepository;

    @PostMapping("/block/{username}")
    public ResponseEntity<?> blockUser(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBlocked(true);
            userRepository.save(user);
            return ResponseEntity.ok("User blocked successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/unblock/{username}")
    public ResponseEntity<?> unblockUser(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBlocked(false);
            userRepository.save(user);
            return ResponseEntity.ok("User unblocked successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
