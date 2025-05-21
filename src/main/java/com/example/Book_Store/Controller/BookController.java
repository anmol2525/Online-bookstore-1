package com.example.Book_Store.Controller;

import com.example.Book_Store.Entity.MyBookList;
import com.example.Book_Store.Entity.User;
import com.example.Book_Store.Service.MyBookListService;
import com.example.Book_Store.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private MyBookListService myBookListService;

    @Autowired
    private UserService userService;

    // Show home page
    @GetMapping("/")
    public String homeRedirect() {
        return "index";
    }

    // Public page to show all available books
    @GetMapping("/availableBooks")
    public String getAllBooks(Model model) {
        List<MyBookList> books = myBookListService.getAllMyBooks();
        model.addAttribute("books", books);
        return "availableBooks";
    }

    // Show book registration form (only for authenticated users)
    @GetMapping("/register")
    public String showRegisterBookForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to register a book.");
            return "redirect:/auth/login";
        }
        model.addAttribute("book", new MyBookList());
        return "bookRegister";
    }

    // Save a new book
    @PostMapping("/save")
    public String saveBook(@ModelAttribute MyBookList myBookList, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first.");
                return "redirect:/auth/login";
            }
            myBookListService.saveMyBooks(myBookList, currentUser);
            redirectAttributes.addFlashAttribute("success", "Book added successfully!");
            return "redirect:/books/availableBooks";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving book: " + e.getMessage());
            return "redirect:/books/register";
        }
    }

    // Show books owned by the logged-in user
    @GetMapping("/my")
    public String getMyBooks(Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to view your books.");
            return "redirect:/auth/login";
        }
        List<MyBookList> books = myBookListService.getMyBooksByUser(currentUser);
        model.addAttribute("books", books);
        return "myBooks";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        MyBookList book = myBookListService.getBookById(id);
        if (book == null || !book.getUser().getId().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized access.");
            return "redirect:/books/my";
        }

        model.addAttribute("book", book);
        return "bookEdit";
    }

    // Update book
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute MyBookList myBookList, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            if (currentUser == null) {
                return "redirect:/auth/login";
            }
            myBookListService.updateBook(id, myBookList, currentUser);
            redirectAttributes.addFlashAttribute("success", "Book updated successfully!");
            return "redirect:/books/my";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating book: " + e.getMessage());
            return "redirect:/books/edit/" + id;
        }
    }

    // Delete book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            if (currentUser == null) {
                return "redirect:/auth/login";
            }

            myBookListService.deleteBookByIdAndUser(id, currentUser);
            redirectAttributes.addFlashAttribute("success", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/books/my";
    }

    // Get currently authenticated User entity (not just Spring UserDetails)
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // Unwrap Optional<User> using orElseThrow
                return userService.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        }
        return null;
    }

}
