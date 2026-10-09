package com.example.swiggy.controller;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.swiggy.request.CustomerRequest;
import com.example.swiggy.response.ApiResponse;
import com.example.swiggy.response.CustomerResponse;
import com.example.swiggy.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response =
                customerService.createCustomer(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>>
            getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse>
            getCustomerById(@PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse>
            updateCustomer(
                @PathVariable Long id,
                @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
            deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                "Customer deleted successfully"
        );
    }
    
    @GetMapping("/with-orders")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomersWithOrders() {

        List<CustomerResponse> customers =
                customerService.getAllCustomersWithOrders();

        ApiResponse<List<CustomerResponse>> response =
                new ApiResponse<>(
                        true,
                        "Customers fetched with orders successfully",
                        customers,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }
}