package com.springuploadfile.service;

import com.springuploadfile.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {

    private final List<Product> products;

    public ProductService() {
        products = new ArrayList<>();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Product findById(int id) {
        return products.get(id);
    }

    @Override
    public void update(int id, Product product) {
        int index = products.indexOf(findById(id));
        products.set(index, product);
    }

    @Override
    public void remove(int id) {
        products.remove(findById(id));
    }
}
