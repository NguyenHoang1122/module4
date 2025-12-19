package com.customerprovincemanagement.controller;

import com.customerprovincemanagement.model.Customer;
import com.customerprovincemanagement.model.Province;
import com.customerprovincemanagement.service.ICustomerService;
import com.customerprovincemanagement.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICustomerService customerService;

    @ModelAttribute("provinces")
    public Iterable<Province> listProvince() {
        return provinceService.findAll();
    }

    @GetMapping("/page")
    public ModelAndView listPageCustomer(@RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(0);
        Pageable pageable = PageRequest.of(currentPage, 3);
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView listCustomersSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Customer> customers;
        if(search.isPresent()){
            customers = customerService.findAllByFirstNameContaining(pageable, search.get());
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String saveCustomer(@ModelAttribute("customer") Customer customer,
                                        RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "New customer created successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if(!customer.isPresent()){
            return new ModelAndView("/error-404");
        }else {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        }
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@ModelAttribute("customer") Customer customer,
                                         RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Customer updated successfully!");
        return "redirect:/customers";
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteCustomer(@PathVariable("id") Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if(!customer.isPresent()){
            return new ModelAndView("/error-404");
        }else {
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        }
    }
}
