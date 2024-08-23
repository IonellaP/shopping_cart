package com.ShoppingCart.cart.service;

import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.model.Product;
import com.ShoppingCart.cart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO convertToDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product,ProductDTO.class);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

}
