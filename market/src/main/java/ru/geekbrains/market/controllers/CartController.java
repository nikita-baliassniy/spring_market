package ru.geekbrains.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.dto.CartDto;
import ru.geekbrains.market.exceptions_handling.ResourceNotFoundException;
import ru.geekbrains.market.model.Cart;
import ru.geekbrains.market.services.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public UUID createNewCart() {
        Cart cart = cartService.save(new Cart());
        return cart.getId();
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        Cart cart = cartService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + uuid));
        return new CartDto(cart);
    }

    @GetMapping("/{uuid}/add/{product_id}")
    public void addProductToCart(@PathVariable UUID uuid, @PathVariable(name = "product_id") Long productId) {
        cartService.addToCart(uuid, productId);
    }

    @DeleteMapping("/{uuid}/remove/{product_id}")
    public void removeFromCart(@PathVariable UUID uuid, @PathVariable(name = "product_id") Long productId) {
        cartService.removeFromCart(uuid, productId);
    }

    @DeleteMapping("/{uuid}/removeTotal/{product_id}")
    public void removeFromCartTotally(@PathVariable UUID uuid, @PathVariable(name = "product_id") Long productId) {
        cartService.removeFromCartTotally(uuid, productId);
    }

    @PostMapping("/{uuid}/clear")
    public void clearCart(@PathVariable UUID uuid) {
        cartService.clearCart(uuid);
    }
}
