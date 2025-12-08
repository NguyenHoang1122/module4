package com.productmanager.service;

import com.productmanager.model.Product;

import java.util.List;

public interface IProductService {
    // find all products
    List<Product> findAll();
    // save product
    void save(Product product);
    // find product by id
    Product findById(int id);
    // update product
    void update(int id, Product product);
    // remove product
    void remove(int id);
    // search product by name
    List<Product> searchByName(String name);
}
