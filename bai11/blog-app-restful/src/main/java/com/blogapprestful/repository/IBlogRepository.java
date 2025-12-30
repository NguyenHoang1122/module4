package com.blogapprestful.repository;

import com.blogapprestful.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IBlogRepository extends JpaRepository<Blog , Long> {
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findAllByTitleContaining(Pageable pageable, String title);
}
