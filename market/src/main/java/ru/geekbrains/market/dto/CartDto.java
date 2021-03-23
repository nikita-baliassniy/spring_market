package ru.geekbrains.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.geekbrains.market.model.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CartDto {
    private List<CartItemDto> items;
    private double totalPrice;

    public CartDto(Cart cart) {
        this.totalPrice = cart.getPrice();
        this.items = cart.getItems().stream().map(CartItemDto::new).collect(Collectors.toList());
    }
}

