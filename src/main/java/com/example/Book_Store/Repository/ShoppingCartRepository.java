package com.example.Book_Store.Repository;


import com.example.Book_Store.Entity.ShoppingCart;
import com.example.Book_Store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findByUser(User user);  // Custom method to find cart by user
}
