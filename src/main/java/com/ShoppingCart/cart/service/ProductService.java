package com.ShoppingCart.cart.service;


import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.entity.Product;
import com.ShoppingCart.cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO create(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .ammount(dto.getAmmount())
                .build();
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public List<ProductDTO> readAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO update(ProductDTO dto) {
        Product product = Product.builder()
                .id(dto.getId()) // Ensure that Product.builder() includes an id setter
                .name(dto.getName())
                .ammount(dto.getAmmount())
                .build();
        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .ammount(product.getAmmount())
                .build();
    }
}
