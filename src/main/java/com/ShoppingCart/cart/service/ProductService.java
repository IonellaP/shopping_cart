package com.ShoppingCart.cart.service;

import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.entity.Product;
import com.ShoppingCart.cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public static Product create(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .ammount((dto.getAmmount()))
                .build();
        return productRepository.save(product);
    }

    public List<Product>readAll() {
        return productRepository.findAll();
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
