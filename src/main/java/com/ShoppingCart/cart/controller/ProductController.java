package com.ShoppingCart.cart.controller;

import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        ProductDTO productDTO = productService.getProductById(id, locale);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.saveProduct(productDTO);
        return ResponseEntity.ok(savedProductDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        productService.deleteProductById(id, locale);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/default")
    public ResponseEntity<ProductDTO> createDefaultProduct() {
        ProductDTO defaultProductDTO = productService.createDefaultProduct();
        return ResponseEntity.ok(defaultProductDTO);
    }
}