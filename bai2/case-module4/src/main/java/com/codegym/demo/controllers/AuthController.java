package com.codegym.demo.controllers;

import com.codegym.demo.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/login")
    public String showFormLogin(@RequestParam(name = "error", required = false) String error,
                                Model model) {
        if (error != null){
            model.addAttribute("errorMessage", "Invalid username or password. Please try again.");
        }
        // Return the name of the view for the login page
        return "auth/login"; // This will resolve to /WEB-INF/views/login.html
    }

    @PostMapping("/login")
    public String submitLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        if (!authService.checkAccount(username, password)) {
            return "redirect:/auth/login?error=true";
        }
        return "redirect:/home";

    }
}
