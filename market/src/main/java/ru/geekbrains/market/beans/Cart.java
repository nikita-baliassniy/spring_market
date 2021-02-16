package ru.geekbrains.market.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.market.exceptions_handling.ResourceNotFoundException;
import ru.geekbrains.market.model.OrderItem;
import ru.geekbrains.market.model.Product;
import ru.geekbrains.market.repositories.OrderRepository;
import ru.geekbrains.market.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


@Component
@Data
@RequiredArgsConstructor
public class Cart {
    private final ProductService productService;
    private List<OrderItem> items;
    private double totalCost;
    private final OrderRepository orderRepository;

    @PostConstruct
    private void init() {
        this.items = new ArrayList<>();
    }

    public void addToCart(Long id) {
        for (OrderItem o : items) {
            if (o.getProduct().getId().equals(id)) {
                o.incrementQuantity();
                recalculate();
                return;
            }
        }
        Product p = productService.findProductById(id).orElseThrow(()
                -> new ResourceNotFoundException("Unable to find product with id: " + id + " (add to cart)"));
        OrderItem orderItem = new OrderItem(p);
        items.add(orderItem);
        recalculate();
    }

    public void removeFromCart(Long id) {
        ListIterator<OrderItem> iterator = items.listIterator();
        while (iterator.hasNext()) {
            OrderItem currentOrderItem = iterator.next();
            if (currentOrderItem.getProduct().getId().equals(id)) {
                if (currentOrderItem.getQuantity() > 1) {
                    currentOrderItem.decrementQuantity();
                } else if (currentOrderItem.getQuantity() == 1) {
                    iterator.remove();
                }
                recalculate();
            }
        }
    }

    public void removeFromCartTotally(Long id) {
        ListIterator<OrderItem> iterator = items.listIterator();
        while (iterator.hasNext()) {
            OrderItem currentOrderItem = iterator.next();
            if (currentOrderItem.getProduct().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    public void recalculate() {
        totalCost = 0;
        for (OrderItem o : items) {
            totalCost += o.getCost();
        }
    }
}
