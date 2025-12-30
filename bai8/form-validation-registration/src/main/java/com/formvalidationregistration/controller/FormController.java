package com.formvalidationregistration.controller;

import com.formvalidationregistration.model.User;
import com.formvalidationregistration.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
    private final IUserService userService;

    @Autowired
    public FormController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/user/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        userService.save(user);
        model.addAttribute("user", user);
        return "result";
    }
}
