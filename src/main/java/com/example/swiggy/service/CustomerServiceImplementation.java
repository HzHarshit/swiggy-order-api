package com.example.swiggy.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CachePut;
import com.example.swiggy.entity.Customer;
import com.example.swiggy.exception.OrderNotFoundException;
import com.example.swiggy.repository.CustomerRepository;
import com.example.swiggy.request.CustomerRequest;
import com.example.swiggy.response.CustomerResponse;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Customer savedCustomer =
                customerRepository.save(customer);

        return convertToResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Cacheable(value = "customers", key = "#id")
    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Customer not found with id: " + id
                    )
                );

        return convertToResponse(customer);
    }

    
    @CachePut(value = "customers", key = "#id")
    @Override
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Customer not found with id: " + id
                    )
                );

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Customer updatedCustomer =
                customerRepository.save(customer);

        return convertToResponse(updatedCustomer);
    }

    
    @CacheEvict(value = "customers", key = "#id")
    @Override
    public void deleteCustomer(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new RuntimeException(
                "Customer not found with id: " + id
            );
        }

        customerRepository.deleteById(id);
    }
    
    @Override
    public List<CustomerResponse> getAllCustomersWithOrders() {

        return customerRepository.findAllCustomersWithOrders()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private CustomerResponse convertToResponse(
            Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}