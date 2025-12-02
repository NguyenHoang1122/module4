package com.customermanagementstoredprocedure.service;

import com.customermanagementstoredprocedure.model.Customer;

public interface ICustomerService {
    boolean saveWithStoredProcedure(Customer customer);
}