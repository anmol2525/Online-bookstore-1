package com.example.Book_Store.Repository;

import com.example.Book_Store.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // Custom methods can be added if needed
}
