package com.example.Book_Store.Service;

import com.example.Book_Store.Entity.Book;
import com.example.Book_Store.Entity.CartItem;
import com.example.Book_Store.Entity.Order;
import com.example.Book_Store.Entity.User;
import com.example.Book_Store.Repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // Get all cart items of a user
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    // Add item to cart
    public void addToCart(User user, Book book, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

 
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // Update cart item quantity
    public void updateCartItemQuantity(User user, Integer bookId, int quantity) {
        CartItem cartItem = cartItemRepository.findByUserAndBookId(user, bookId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    // Checkout - create an order
    public Order checkout(User user, String shippingAddress, String paymentMethod) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setCartItems(getCartItems(user));  // Assuming order contains cart items
        // Add additional logic to save the order and clear the cart
        return order;
    }
}
