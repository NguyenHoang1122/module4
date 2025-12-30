package com.shoppingcartflower.service;

import com.shoppingcartflower.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService{
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
    public Product findById(Long id) {
        return products.get(id);
    }

    @Override
    public void update(Long id, Product product) {
        int index = products.indexOf(findById(id));
        products.set(index, product);
    }

    @Override
    public void remove(Long id) {
        products.remove(findById(id));
    }
}
