package com.ShoppingCart.cart.service;

import com.ShoppingCart.cart.model.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartService {
    private List<Product> cartItems;

    public CartService() {
        this.cartItems = new ArrayList<>();
    }

    public void addToCart(Product product) {
        cartItems.add(product);
    }

    public void removeFromCard(Long productId) {
        cartItems.removeIf(product -> product.getId().equals(productId));
    }

}
