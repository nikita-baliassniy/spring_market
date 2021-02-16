package ru.geekbrains.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.market.beans.Cart;
import ru.geekbrains.market.model.Order;
import ru.geekbrains.market.model.Address;
import ru.geekbrains.market.model.User;
import ru.geekbrains.market.repositories.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final Cart cart;

    public Order createFromUserCart(User user, Address address) {
        address = addressService.save(address);
        Order order = new Order(cart, user, address);
        order = orderRepository.save(order);
        cart.clear();
        return order;
    }

    public List<Order> findAllOrdersByOwnerName(String username) {
        return orderRepository.findAllByOwnerUsername(username);
    }

}
