package com.ShoppingCart.cart.dto;

import com.ShoppingCart.cart.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Short ammount;

    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .ammount(product.getAmmount())
                .build();
    }
}
