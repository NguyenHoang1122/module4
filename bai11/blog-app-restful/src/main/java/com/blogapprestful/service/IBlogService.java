package com.blogapprestful.service;

import com.blogapprestful.model.Blog;
import com.blogapprestful.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBlogService extends IGenerateService<Blog> {
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findAllByTitleContaining(Pageable pageable, String title);
}

