package com.currencyconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConverterController {
    @GetMapping("/")
    public String showConverterPage() {
        return "index";
    }

    @PostMapping("/convert")
    public String convertCurrency(@RequestParam("rate") double rate,
                                  @RequestParam("usd") double usd,
                                  Model model){
        double result = rate * usd;
        model.addAttribute("rate", rate);
        model.addAttribute("usd", usd);
        model.addAttribute("result", result);
        return "result";
    }
}
