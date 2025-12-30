package com.shoppingcartflower.controller;

import com.shoppingcartflower.model.Product;
import com.shoppingcartflower.model.ProductForm;
import com.shoppingcartflower.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public String listProduct(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "/list";
    }

    @GetMapping("/create")
    public ModelAndView createProduct(){
        ModelAndView modelAndView = new ModelAndView("/create");
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
        Product product = new Product(productForm.getId(), productForm.getName(),
                productForm.getPrice(), fileName);
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        modelAndView.addObject("productForm", productForm);
        return modelAndView;
    }
}
