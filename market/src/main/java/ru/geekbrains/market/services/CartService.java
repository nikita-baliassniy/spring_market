package ru.geekbrains.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.market.exceptions_handling.ResourceNotFoundException;
import ru.geekbrains.market.model.Cart;
import ru.geekbrains.market.model.CartItem;
import ru.geekbrains.market.model.Product;
import ru.geekbrains.market.repositories.CartRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findById(UUID id) {
        return cartRepository.findById(id);
    }

    @Transactional
    public void addToCart(UUID cartId, Long productId) {
        Product p = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to add product with id: " + productId + " to cart. Product doesn't exist"));
        Cart cart = findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to find cart with id: " + cartId));;
        cart.add(new CartItem(p));
    }

    @Transactional
    public void removeFromCart(UUID cartId, Long productId){
        Product p = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to remove product with id: " + productId + " to cart. Product doesn't exist"));
        Cart cart = findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to find cart with id: " + cartId));;
        cart.removeFromCart(productId);
    }

    @Transactional
    public void clearCart(UUID cartId){
        Cart cart = findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to find cart with id: " + cartId));;
        cart.clear();
    }

    @Transactional
    public void removeFromCartTotally(UUID cartId, Long productId) {
        Product p = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to remove product with id: " + productId + " to cart. Product doesn't exist"));
        Cart cart = findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Unable to find cart with id: " + cartId));;
        cart.removeFromCartTotally(productId);
    }



}