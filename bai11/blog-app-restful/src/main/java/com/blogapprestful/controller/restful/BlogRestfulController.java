package com.blogapprestful.controller.restful;

import com.blogapprestful.model.Blog;
import com.blogapprestful.service.IBlogService;
import com.blogapprestful.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/blogs")
public class BlogRestfulController {
    @Autowired
    private  IBlogService blogService;

    @GetMapping
    public ResponseEntity<Iterable<Blog>> getAllBlogs(){
        List<Blog> blogs = (List<Blog>) blogService.findAll();
        if(blogs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findBlogById(@PathVariable("id") Long id){
        Optional<Blog> blog = blogService.findById(id);
        if(!blog.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog.get(), HttpStatus.OK);
    }
}