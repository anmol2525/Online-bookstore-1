package com.example.Book_Store.Controller;

import com.example.Book_Store.Entity.*;
import com.example.Book_Store.Service.CartService;
import com.example.Book_Store.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(@RequestParam("userId") Long userId, Model model) {
        User user = userService.findById(userId);
        List<CartItem> cartItems = cartService.getCartItems(user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        return "cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addToCart(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int quantity) {
        User user = userService.findById(userId);
        Book book = new Book();
        book.setId(bookId);
        cartService.addToCart(user, book, quantity);
        return "Item added to cart";
    }

    @PostMapping("/remove/{cartItemId}")
    @ResponseBody
    public String removeFromCart(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "Item removed from cart";
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateCartItemQuantity(@RequestParam Long userId, @RequestParam Integer bookId, @RequestParam int quantity) {
        User user = userService.findById(userId);
        cartService.updateCartItemQuantity(user, bookId, quantity);
        return "Cart item updated";
    }

    @PostMapping("/checkout")
    @ResponseBody
    public Order checkout(@RequestParam Long userId, @RequestParam String shippingAddress, @RequestParam String paymentMethod) {
        User user = userService.findById(userId);
        return cartService.checkout(user, shippingAddress, paymentMethod);
    }
}
