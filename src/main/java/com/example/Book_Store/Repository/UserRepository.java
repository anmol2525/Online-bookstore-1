package com.example.Book_Store.Repository;



import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Book_Store.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
