package com.ktmodule4.repository;

import com.ktmodule4.model.Customer;
import com.ktmodule4.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByCustomer_NameContainingAndType(String name, String Type);
}
