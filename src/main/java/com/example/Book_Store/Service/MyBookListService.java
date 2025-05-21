package com.example.Book_Store.Service;

import com.example.Book_Store.Entity.MyBookList;
import com.example.Book_Store.Entity.User;
import com.example.Book_Store.Repository.MyBookListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyBookListService {

    @Autowired
    private MyBookListRepository myBookListRepository;

    public MyBookList saveMyBooks(MyBookList myBookList, User user) {
        myBookList.setUser(user); // Associate the book with the current user
        return myBookListRepository.save(myBookList); // Save the book to the repository
    }

    public List<MyBookList> getMyBooksByUser(User user) {
        return myBookListRepository.findByUser(user); // Get all books associated with the current user
    }

    public List<MyBookList> getAllMyBooks() {
        return myBookListRepository.findAll(); // Retrieve all books
    }

    public void deleteBookByIdAndUser(Long id, User user) {
        Optional<MyBookList> optionalBook = myBookListRepository.findById(id);
        if (optionalBook.isPresent()) {
            MyBookList book = optionalBook.get();
            if (book.getUser().getId().equals(user.getId())) {
                myBookListRepository.deleteById(id); // Delete book if it belongs to the user
            }
        }
    }

    public MyBookList getBookById(Long id) {
        return myBookListRepository.findById(id).orElse(null); // Retrieve book by ID
    }
}
