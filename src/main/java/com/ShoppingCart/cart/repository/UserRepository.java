package com.ShoppingCart.cart.repository;

import com.ShoppingCart.cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
