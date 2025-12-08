package com.productmanager.controller;

import com.productmanager.model.Product;
import com.productmanager.service.IProductService;
import com.productmanager.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService = new ProductService();

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "search", required = false) String search) {
       List<Product> products;
         if (search != null && !search.isEmpty()) {
             products = productService.searchByName(search);
             model.addAttribute("search", search);
         } else {
             products = productService.findAll();
         }
         model.addAttribute("products", products);
         return "/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Product product, RedirectAttributes redirect) {
        product.setId((int) (Math.random() * 10000));
        productService.save(product);
        redirect.addFlashAttribute("success", "Thêm mới sản phẩm thành công !");
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/update";
    }

    @PostMapping("/update")
    public String update(Product product, RedirectAttributes redirect) {
        productService.update(product.getId(), product);
        redirect.addFlashAttribute("success", "Cập nhật sản phẩm thành công !");
        return "redirect:/products";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(Product product, RedirectAttributes redirect) {
        productService.remove(product.getId());
        redirect.addFlashAttribute("success", "Xóa sản phẩm thành công !");
        return "redirect:/products";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/view";
    }
}
