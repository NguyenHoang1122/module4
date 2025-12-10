package com.springformtutorial.controller;

import com.springformtutorial.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/show-form")
    public String showForm(Model model){
        model.addAttribute("employee",new Employee());
        return "/employee/create";
    }

    @PostMapping("/add-employee")
    public String submit(@ModelAttribute("employee") Employee employee, Model model){
        model.addAttribute("name",employee.getName());
        model.addAttribute("contactNumber",employee.getContactNumber());
        model.addAttribute("id",employee.getId());
        return "/employee/info";
    }
}
