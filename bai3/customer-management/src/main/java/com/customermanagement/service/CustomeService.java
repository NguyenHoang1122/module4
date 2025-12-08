package com.customermanagement.service;

import com.customermanagement.model.Customer;

import java.util.List;

public interface CustomeService {
    List<Customer> findAll();
    Customer findById(Long id);
    void save(Customer customer);
}
