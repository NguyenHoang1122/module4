package com.customermanageexceptionhandler.controller;

import com.customermanageexceptionhandler.exception.DuplicateEmailException;
import com.customermanageexceptionhandler.model.Customer;
import com.customermanageexceptionhandler.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @GetMapping("")
    public String index(Model model){
        List<Customer> customers = iCustomerService.findAll();
        model.addAttribute("customers",customers);
        return "/index";
    }

    @GetMapping("/create")
    public String create( Model model){
        model.addAttribute("customer",new Customer());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Customer customer) throws DuplicateEmailException{
        iCustomerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", iCustomerService.findById(id));
        return "/update";
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", iCustomerService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(Customer customer, RedirectAttributes redirect) {
        iCustomerService.remove(customer.getId());
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("customer", iCustomerService.findById(id));
        return "/view";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/inputs-not-acceptable");
    }
}
