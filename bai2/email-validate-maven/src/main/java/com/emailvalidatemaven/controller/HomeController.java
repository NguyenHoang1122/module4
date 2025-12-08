package com.emailvalidatemaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
public class HomeController {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    private static Pattern pattern;

    public HomeController() {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    private boolean validate(String regex){
        return pattern.matcher(regex).matches();
    }

    @PostMapping("/validate")
    public String userValidate(@RequestParam("email") String email,
                               ModelMap modelMap){
        boolean isValid = this.validate(email);

        if(!isValid){
            modelMap.addAttribute("message","Email không đúng định dạng");
            return "home";
        } else {
           modelMap.addAttribute("email", email);
              return "success";
        }
    }
}
