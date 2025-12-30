package com.blogapprestful.repository;

import com.blogapprestful.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends CrudRepository<Category,Long> {

}
