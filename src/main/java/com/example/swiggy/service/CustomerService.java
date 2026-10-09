package com.example.swiggy.service;

import java.util.List;

import com.example.swiggy.request.CustomerRequest;
import com.example.swiggy.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);
    
    List<CustomerResponse> getAllCustomersWithOrders();
}