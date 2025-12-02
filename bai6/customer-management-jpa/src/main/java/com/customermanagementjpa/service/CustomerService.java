package com.customermanagementjpa.service;

import com.customermanagementjpa.model.Customer;
import com.customermanagementjpa.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private ICustomerRepository icustomerRepository;

    @Override
    public List<Customer> findAll() {
        return icustomerRepository.findAll();
    }

    @Override
    public void save(Customer customer) {
        icustomerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return icustomerRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        icustomerRepository.remove(id);
    }
}
