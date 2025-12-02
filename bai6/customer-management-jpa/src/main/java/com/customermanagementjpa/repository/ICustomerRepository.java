package com.customermanagementjpa.repository;

import com.customermanagementjpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends IGenerateRepository<Customer> {
}
