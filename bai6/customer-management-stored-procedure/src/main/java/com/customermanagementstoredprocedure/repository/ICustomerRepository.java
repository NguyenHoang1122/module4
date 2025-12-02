package com.customermanagementstoredprocedure.repository;

import com.customermanagementstoredprocedure.model.Customer;

public interface ICustomerRepository {
    boolean saveWithStoredProcedure(Customer customer);
}
