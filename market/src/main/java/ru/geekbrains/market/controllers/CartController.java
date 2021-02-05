package ru.geekbrains.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.beans.Cart;
import ru.geekbrains.market.dto.CartDto;
import ru.geekbrains.market.model.User;
import ru.geekbrains.market.services.ProductService;
import ru.geekbrains.market.services.UserService;


import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final Cart cart;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cart.addToCart(id);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cart.removeFromCart(id);
    }

    @GetMapping("/removeTotal/{id}")
    public void removeFromCartTotally(@PathVariable Long id) {
        cart.removeFromCartTotally(id);
    }

    @GetMapping
    public CartDto getCart() {
        return new CartDto(cart);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cart.clear();
    }

    @PostMapping("/create_order")
    public Long createOrderFromCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                new RuntimeException("unable to find user by username: " + principal.getName()));
        return cart.createOrder(user.getUsername());
    }
}
