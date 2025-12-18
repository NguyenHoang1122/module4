package com.customerprovincemanagement.repository;

import com.customerprovincemanagement.model.Customer;
import com.customerprovincemanagement.model.Province;
import org.springframework.data.repository.CrudRepository;

public interface IProvinceRepository extends CrudRepository<Province,Long> {
}
