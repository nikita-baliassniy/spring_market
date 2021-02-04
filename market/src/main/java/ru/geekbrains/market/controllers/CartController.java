package ru.geekbrains.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.beans.Cart;
import ru.geekbrains.market.dto.CartDto;
import ru.geekbrains.market.services.ProductService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
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

}
