package com.ShoppingCart.cart.service;

import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.entity.Product;
import com.ShoppingCart.cart.exception.ResourceNotFoundException;
import com.ShoppingCart.cart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO convertToDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToDto(product);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }
}
