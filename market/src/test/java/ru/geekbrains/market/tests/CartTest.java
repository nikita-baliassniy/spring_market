package ru.geekbrains.market.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.market.model.Cart;
import ru.geekbrains.market.model.CartItem;

import java.util.ArrayList;

@SpringBootTest(classes = Cart.class)
public class CartTest {
    @Autowired
    private Cart cart;

    @Test
    public void cartTestAddingProducts() {
        cart.setItems(new ArrayList<>());
        long testSum = 0;
        for (long i = 1; i <= 5; i++) {
            CartItem cartItem = new CartItem();
            cartItem.setId(i);
            cartItem.setPrice(i * 10);
            testSum += i * 10;
            cart.add(cartItem);
        }
        Assertions.assertEquals(testSum, cart.getPrice());
        Assertions.assertEquals(5, cart.getItems().size());
    }

    @Test
    public void cartTestRemovingProducts() {
        cart.setItems(new ArrayList<>());
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(10);
        cartItem.setPricePerProduct(10);
        cartItem.setPrice(100);
        cart.add(cartItem);
        cart.removeFromCart(1L);
        cart.removeFromCart(1L);
        Assertions.assertEquals(80, cart.getPrice());
        Assertions.assertEquals(8, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void cartTestTotalRemovingProducts() {
        cart.setItems(new ArrayList<>());
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(10);
        cartItem.setPricePerProduct(10);
        cartItem.setPrice(100);
        cart.add(cartItem);
        cart.removeFromCartTotally(1L);
        Assertions.assertEquals(0, cart.getPrice());
    }
}
