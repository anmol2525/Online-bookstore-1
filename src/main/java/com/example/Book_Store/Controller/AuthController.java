package com.example.Book_Store.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Book_Store.Entity.User;
import com.example.Book_Store.Service.UserService;
import com.example.Book_Store.Validator.UserValidator;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    // Show login form
    @GetMapping("/auth/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "logout", required = false) String logout,
                                 @RequestParam(value = "registered", required = false) String registered,
                                 Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        if (registered != null) {
            model.addAttribute("message", "Registration successful! Please login.");
        }
        return "login";  // maps to login.html or login.jsp
    }

    // Show user registration form
    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // maps to register.html or register.jsp
    }

    // Handle user registration submission
    @PostMapping("/auth/register")
    public String registerUser(@ModelAttribute("user") User user, 
                               BindingResult result, 
                               Model model) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(user);
            return "redirect:/auth/login?registered";  // Redirect to login with message
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
