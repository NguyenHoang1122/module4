package com.productmanager.service;

import com.productmanager.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService implements IProductService{

    private static final Map<Integer, Product> productList;

    static {
        productList = new HashMap<>();
        productList.put(1, new Product(1, "Laptop", 1000.0, "High performance laptop", "Dell"));
        productList.put(2, new Product(2, "Smartphone", 700.0, "Latest model smartphone", "Samsung"));
        productList.put(3, new Product(3, "Tablet", 400.0, "Lightweight tablet", "Apple"));
        productList.put(4, new Product(4, "Headphones", 150.0, "Noise-cancelling headphones", "Sony"));
        productList.put(5, new Product(5, "Smartwatch", 200.0, "Feature-rich smartwatch", "Fitbit"));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productList.values());
    }

    @Override
    public void save(Product product) {
        productList.put(product.getId(), product);
    }

    @Override
    public Product findById(int id) {
        return productList.get(id);
    }

    @Override
    public void update(int id, Product product) {
        productList.put(id, product);
    }

    @Override
    public void remove(int id) {
        productList.remove(id);
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product product : productList.values()) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }
}
