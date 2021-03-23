package ru.geekbrains.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.market.model.CartItem;

@NoArgsConstructor
@Data
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private double pricePerProduct;
    private double price;

    public CartItemDto(CartItem cartItem) {
        this.productId = cartItem.getProduct().getId();
        this.productTitle = cartItem.getProduct().getTitle();
        this.quantity = cartItem.getQuantity();
        this.pricePerProduct = cartItem.getPricePerProduct();
        this.price = cartItem.getPrice();
    }
}