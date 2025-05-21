package com.example.Book_Store.Repository;

import com.example.Book_Store.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom methods can be added if needed
}
