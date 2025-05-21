package com.example.Book_Store.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Book_Store.Entity.Book;
import com.example.Book_Store.Entity.User;
import com.example.Book_Store.Repository.BookRepository;
import com.example.Book_Store.Repository.UserRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Save a book and associate it with the current logged-in user
    public void save(Book book, User currentUser) {
        // Associate the logged-in user with the book
        book.setUser(currentUser);
        bookRepository.save(book);
    }

    // Get all books from the database
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by its ID
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    // Delete a book by its ID
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}
