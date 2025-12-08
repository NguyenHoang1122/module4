package com.springmvccustomermanagement.controller;

import com.springmvccustomermanagement.model.Customer;
import com.springmvccustomermanagement.service.CustomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomeService customeService;

    @GetMapping("/customers")
    public ModelAndView showlist(){
        ModelAndView modelAndView = new ModelAndView("list");
        List<Customer> customers = customeService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/customers/detail")
    public ModelAndView showDetail(@RequestParam("id") Integer customerId){
        ModelAndView modelAndView = new ModelAndView("info");
        Customer customer = customeService.findById(customerId);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

}
