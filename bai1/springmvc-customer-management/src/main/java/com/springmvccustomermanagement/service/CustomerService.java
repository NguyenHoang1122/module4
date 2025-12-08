package com.springmvccustomermanagement.service;

import com.springmvccustomermanagement.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(int id);
}
