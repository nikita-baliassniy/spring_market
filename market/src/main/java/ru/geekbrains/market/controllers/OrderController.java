package ru.geekbrains.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.dto.OrderDto;
import ru.geekbrains.market.exceptions_handling.ResourceNotFoundException;
import ru.geekbrains.market.model.Address;
import ru.geekbrains.market.model.User;
import ru.geekbrains.market.services.OrderService;
import ru.geekbrains.market.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    /**
     * Понятно, что для одного string адреса не обязательно было создавать отдельную модель, но создал с расчетом на то,
     * что потом там будут отдельные поля для города, индекса, дома, квартиры и т.д.
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrderFromCart(Principal principal, @RequestBody Address address) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User not found " + principal.getName()));
        orderService.createFromUserCart(user, address);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findAllOrdersByOwnerName(principal.getName())
                .stream().map(OrderDto::new).collect(Collectors.toList());
    }

}
