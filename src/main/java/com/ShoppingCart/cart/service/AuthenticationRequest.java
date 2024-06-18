package com.ShoppingCart.cart.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
        private String username;
        private String password;

        public AuthenticationRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

}
