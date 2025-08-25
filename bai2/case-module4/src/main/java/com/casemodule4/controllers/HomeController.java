package com.casemodule4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping({"/","/home"})
    public String showHomePage() {
        // Return the name of the view for the home page
        return "/home"; // This will resolve to /WEB-INF/views/home.jsp
    }
}