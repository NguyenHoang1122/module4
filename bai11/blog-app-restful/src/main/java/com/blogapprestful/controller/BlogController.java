package com.blogapp.controller;

import com.blogapp.model.Blog;
import com.blogapp.service.IBlogService;
import com.blogapp.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService iBlogService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/page")
    public ModelAndView listPagedBlogs(@RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(0);
        Pageable pageable = PageRequest.of(currentPage, 4);
        Page<Blog> blogs = iBlogService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("blog/list");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView listBlogsSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Blog> blogs;
        if(search.isPresent()){
            blogs = iBlogService.findAllByTitleContaining(pageable, search.get());
        } else {
            blogs = iBlogService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("blog/list");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/filter")
    public ModelAndView filterByTitle(@RequestParam("title") Optional<String> title,
                                      @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(0);
        Pageable pageable = PageRequest.of(currentPage, 4);
        Page<Blog> blogs;

        if (title.isPresent() && !title.get().isEmpty()) {
            blogs = iBlogService.findAllByTitleContaining(pageable, title.get());
        } else {
            blogs = iBlogService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("blog/list");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping
    public String listBlogs(Model model) {
        model.addAttribute("blogs", iBlogService.findAll());
        return "blog/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("categories", categoryService.findAll());
        return "blog/create";
    }

    @PostMapping
    public String saveBlog(@ModelAttribute("blog") Blog blog) {
        iBlogService.save(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/{id}")
    public String viewBlog(@PathVariable("id") Long id, Model model) {
        Optional<Blog> blog = iBlogService.findById(id);
        if (blog.isEmpty()) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog", blog.get());
        return "blog/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Blog> blog = iBlogService.findById(id);
        if (blog.isEmpty()) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog", blog.get());
        model.addAttribute("categories", categoryService.findAll());
        return "blog/edit";
    }

//    Optional<Blog> optionalBlog = iBlogService.findById(id);
//        if (optionalBlog.isPresent()) {
//        Blog blog = optionalBlog.get();
//        model.addAttribute("blog", blog);
//        model.addAttribute("categories", categoryService.findAll());
//        return "blog/edit";
//    }
//        return "redirect:/blogs";


    @PostMapping("/{id}/update")
    public String updateBlog(@PathVariable("id") Long id, @ModelAttribute("blog") Blog blog) {
        blog.setId(id);
        iBlogService.update(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/delete")
    public String deleteBlog(@PathVariable("id") Long id) {
        iBlogService.remove(id);
        return "redirect:/blogs";
    }
}
