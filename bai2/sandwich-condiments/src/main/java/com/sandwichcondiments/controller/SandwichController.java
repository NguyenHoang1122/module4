package com.sandwichcondiments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SandwichController {
    @GetMapping("/")
    public String showSandwichForm() {
        return "home";
    }

    @PostMapping("/validate")
    public String validateSandwich(@RequestParam(value = "condiments", required = false) List<String> condiments, Model model) {
        if (condiments == null) {
            condiments = Collections.emptyList();
        }
        model.addAttribute("condiments", new ArrayList<>(condiments));
        return "success";
    }
}
