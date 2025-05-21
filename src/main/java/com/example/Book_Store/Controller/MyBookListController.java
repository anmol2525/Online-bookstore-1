package com.example.Book_Store.Controller;

import com.example.Book_Store.Service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyBookListController {

    @Autowired
    private MyBookListService service;

    @RequestMapping("/my_books")
    public String getBookList(Model model) {
        model.addAttribute("books", service.getAllMyBooks());
        return "bookList";
    }
}
