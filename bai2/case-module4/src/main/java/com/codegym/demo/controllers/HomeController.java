package com.codegym.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String showHomePage() {
        // Return the name of the view for the home page
        return "index"; // This will resolve to /WEB-INF/views/home.jsp
    }
}
