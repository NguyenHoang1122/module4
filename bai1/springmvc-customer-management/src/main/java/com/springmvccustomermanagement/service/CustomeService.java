package com.springmvccustomermanagement.service;

import com.springmvccustomermanagement.model.Customer;

import java.util.List;

public interface CustomeService {
    List<Customer> findAll();
    Customer findById(int id);
}
