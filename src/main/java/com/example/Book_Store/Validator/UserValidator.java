package com.example.Book_Store.Validator;



import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.Book_Store.Entity.User;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Custom validation for email format
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("email", "email.invalid", "Invalid email format.");
        }

        // Custom validation for password length
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.rejectValue("password", "password.length", "Password must be at least 6 characters long.");
        }

        // You can add more custom validations here as needed
    }
}
