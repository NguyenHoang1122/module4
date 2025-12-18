package com.customerprovincemanagement.service;

import com.customerprovincemanagement.model.Customer;
import com.customerprovincemanagement.model.Province;

public interface ICustomerService extends IGenerateService<Customer>{
    Iterable<Customer> findAllByProvince(Province province);
}
