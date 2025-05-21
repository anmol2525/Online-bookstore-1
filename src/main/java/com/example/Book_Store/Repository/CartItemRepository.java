package com.example.Book_Store.Repository;


import com.example.Book_Store.Entity.CartItem;
import com.example.Book_Store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Custom method to find a CartItem by user and bookId
    CartItem findByUserAndBookId(User user, Integer bookId);

    // Custom method to find all cart items by a user
    List<CartItem> findByUser(User user);
}
