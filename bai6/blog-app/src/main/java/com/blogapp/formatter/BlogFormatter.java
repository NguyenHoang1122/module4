package com.blogapp.formatter;

import com.blogapp.model.Blog;
import com.blogapp.repository.IBlogRepository;
import com.blogapp.service.IBlogService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class BlogFormatter implements Formatter<Blog> {
    private IBlogService iBlogService;

    public BlogFormatter(IBlogService iBlogService) {
        this.iBlogService = iBlogService;
    }
    @Override
    public Blog parse(String text, Locale locale) {
        Optional<Blog> blogOptional = iBlogService.findById(Long.parseLong(text));
        return blogOptional.orElse(null);
    }
    @Override
    public String print(Blog object, Locale locale) {
        return "[" + object.getId() + "," + object.getTitle() + "]";
    }
}
