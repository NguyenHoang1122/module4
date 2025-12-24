// src/main/java/com/librarymanagement/controller/BookController.java
package com.librarymanagement.controller;

import com.librarymanagement.exception.InvalidBorrowCodeException;
import com.librarymanagement.exception.OutOfBookException;
import com.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private IBookService bookService;

    @GetMapping("")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "/book/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/book/view";
    }

    @PostMapping("/borrow/{id}")
    public String borrow(@PathVariable("id") Long id, Model model) {
        String code = bookService.borrowBook(id);
        model.addAttribute("code", code);
        return "/book/borrow-success";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("code") String code, Model model) {
        bookService.returnBook(code);
        return "/book/return-success";
    }

    @ExceptionHandler(InvalidBorrowCodeException.class)
    public ModelAndView handleInvalidBorrowCodeException() {
        return new ModelAndView("/error-invalid-code");
    }

    @ExceptionHandler(OutOfBookException.class)
    public ModelAndView handleOutOfBookException() {
        return new ModelAndView("/error-out-of-book");
    }
}
