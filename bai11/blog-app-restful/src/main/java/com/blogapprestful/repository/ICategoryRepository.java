package com.blogapp.repository;

import com.blogapp.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends CrudRepository<Category,Long> {

}
