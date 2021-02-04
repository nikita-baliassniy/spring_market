package ru.geekbrains.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.market.model.OrderItem;


@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long id;
    private String productTitle;
    private int quantity;
    private double costPerProduct;
    private double cost;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getProduct().getId();
        this.productTitle = orderItem.getProduct().getTitle();
        this.quantity = orderItem.getQuantity();
        this.costPerProduct = orderItem.getCostPerProduct();
        this.cost = orderItem.getCost();
    }
}
