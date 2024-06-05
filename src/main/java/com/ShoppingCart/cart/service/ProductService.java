package com.ShoppingCart.cart.service;

import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.entity.Product;
import com.ShoppingCart.cart.exception.ResourceNotFoundException;
import com.ShoppingCart.cart.repository.ProductRepository;
//import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    private final MessageSource messageSource;


    @Value("${product.defaultName}")
    private String defaultName;

    @Value("${product.defaultAmount}")
    private Short defaultAmount;

    @Autowired
    public ProductService(ModelMapper modelMapper, ProductRepository productRepository, MessageSource messageSource) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.messageSource = messageSource;
    }

    public ProductDTO getProductById(Long id, Locale locale) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("product.not.found", null, locale)
                ));
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public void deleteProductById(Long id, Locale locale) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("product.not.found",null, locale)
                ));
        productRepository.delete(product);
    }

    public ProductDTO createDefaultProduct() {
        Product product = Product.builder()
                .name(defaultName)
                .amount(defaultAmount)
                .build();
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
