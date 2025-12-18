package com.customerprovincemanagement.controller;

import com.customerprovincemanagement.model.Customer;
import com.customerprovincemanagement.model.Province;
import com.customerprovincemanagement.service.ICustomerService;
import com.customerprovincemanagement.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("provinces")
public class ProvinceController {

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView listProvinces(){
        ModelAndView modelAndView = new ModelAndView("province/list");
        Iterable<Province> provinces = provinceService.findAll();
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable("id") Long id) {
        Optional<Province> province = provinceService.findById(id);
        if(province.isPresent()){
            ModelAndView modelAndView = new ModelAndView("province/update");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        }else{
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("province") Province province,
                                       RedirectAttributes redirectAttributes) {
        provinceService.save(province);
        redirectAttributes.addFlashAttribute("message", "Province updated successfully!");
        return "redirect:/provinces";
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("province") Province province,
                                       RedirectAttributes redirectAttributes) {
        provinceService.save(province);
        redirectAttributes.addFlashAttribute("message", "New province created successfully!");
        return "redirect:/provinces";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProvince(@PathVariable("id") Long id){
        Optional<Province> province = provinceService.findById(id);
        if(province.isPresent()){
            ModelAndView modelAndView = new ModelAndView("province/delete");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        }else{
            return new ModelAndView("error-404");
        }
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Optional<Province> province = provinceService.findById(id);
        if(province.isPresent()){
            Iterable<Customer> customers = customerService.findAllByProvince(province.get());
            ModelAndView modelAndView = new ModelAndView("/customer/list");
            modelAndView.addObject("customers", customers);
            return modelAndView;
        }else{
            return new ModelAndView("error-404");
        }
    }
}
