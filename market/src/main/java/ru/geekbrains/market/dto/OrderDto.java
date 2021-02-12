package ru.geekbrains.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.market.model.Order;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String username;
    private double totalCost;
    private String address;
    private String creationDateTime;

    public OrderDto(Order order) {
        this.username = order.getOwner().getUsername();
        this.totalCost = order.getCost();
        this.creationDateTime = order.getCreatedAt().toString();
        this.id = order.getId();
        this.address = order.getAddress().getAddress();
    }

}
