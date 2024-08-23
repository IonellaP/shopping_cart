package com.ShoppingCart.cart.controller;

import com.ShoppingCart.cart.dto.ProductDTO;
import com.ShoppingCart.cart.model.Product;
import com.ShoppingCart.cart.service.ProductService;
import com.ShoppingCart.cart.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private MessageSource messageSource;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    private SessionService sessionService = SessionService.getInstance();

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.convertToEntity(productDTO);

        Product savedProduct = productService.saveProduct(product);

        return ResponseEntity.ok(productService.convertToDTO(savedProduct));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/session/new")
    public ResponseEntity<Long> createNewSession() {
        Long sessionId = sessionService.createNewSession();

        return ResponseEntity.ok(sessionId);
    }

    @PostMapping("session/{sessionId}/art/add/{productId}")
    public ResponseEntity<Void> addToCart(@PathVariable Long sessionId, @PathVariable Long productId) {
        Product product = productService.findProductById(productId);
        if (product!= null) {
            sessionService.getCartBySession(sessionId).addToCart(product);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/session/{sessionId}/cart/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long sessionId, @PathVariable Long productId) {
        sessionService.getCartBySession(sessionId).removeFromCard(productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/session/{sessionId}/cart")
    public ResponseEntity<List<ProductDTO>> viewCart(@PathVariable Long sessionId) {
        List<Product> cartItems = sessionService.getCartBySession(sessionId).getCartItems();
        List<ProductDTO>cartItemsDTO = cartItems.stream().map(productService::convertToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(cartItemsDTO);
    }

    @GetMapping("/notfound")
    public ResponseEntity<String> getProductNotFoundMessage(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {

        String message = messageSource.getMessage("product.not.found", null, locale);

        return ResponseEntity.ok(message);
    }

}
