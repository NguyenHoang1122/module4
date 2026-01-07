package com.blogapp.repository;

import com.blogapp.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface IBlogRepository extends CrudRepository<Blog , Long> {
    Page<Blog> findAll(Pageable pageable); //pageable la de phan trang.
    Page<Blog> findAllByTitleContaining(Pageable pageable, String title);
}
