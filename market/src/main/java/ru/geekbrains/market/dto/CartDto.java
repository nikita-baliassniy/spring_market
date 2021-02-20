package ru.geekbrains.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.geekbrains.market.beans.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CartDto {
    private List<OrderItemDto> items;
    private double totalPrice;

    public CartDto(Cart cart) {
        this.totalPrice = cart.getTotalPrice();
        this.items = cart.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}

