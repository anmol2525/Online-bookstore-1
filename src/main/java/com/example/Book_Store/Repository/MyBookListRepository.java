package com.example.Book_Store.Repository;




import com.example.Book_Store.Entity.MyBookList;
import com.example.Book_Store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MyBookListRepository extends JpaRepository<MyBookList, Long> {
    List<MyBookList> findByUser(User user); // Find books by the associated user
}
