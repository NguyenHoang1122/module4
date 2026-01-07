package com.shoppingcartflower.controller;

import com.shoppingcartflower.model.Cart;
import com.shoppingcartflower.model.Product;
import com.shoppingcartflower.model.ProductForm;
import com.shoppingcartflower.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/product")
@SessionAttributes("cart")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping("")
    public String listProduct(Model model){
        Iterable<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/create")
    public ModelAndView createProduct(){
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute("productForm") ProductForm productForm){
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Product product = new Product(productForm.getId(), productForm.getProductCode(), productForm.getName(),
                productForm.getDescription(), productForm.getPrice(), fileName);
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        modelAndView.addObject("productForm", productForm);
        return modelAndView;
    }
    @GetMapping("/view/{id}")
    public ModelAndView viewProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView("product/view");
        modelAndView.addObject("product", product);
        return modelAndView;
    }
}
