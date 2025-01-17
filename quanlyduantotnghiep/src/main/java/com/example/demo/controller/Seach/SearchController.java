package com.example.demo.controller.Seach;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Book.Book;
import com.example.demo.entity.Borrower.Borrower;
import com.example.demo.service.Book.BookService;
import com.example.demo.service.Borrower.BorrowerService;

@Controller
public class SearchController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowerService borrowerService;

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("keyword") String keyword) {
        List<Book> books = bookService.searchBooks(keyword);
        List<Borrower> borrowers = borrowerService.searchBorrowers(keyword);
        ModelAndView modelAndView = new ModelAndView("search-results");
        modelAndView.addObject("books", books);
        modelAndView.addObject("borrowers", borrowers);
        return modelAndView;
    }
}
