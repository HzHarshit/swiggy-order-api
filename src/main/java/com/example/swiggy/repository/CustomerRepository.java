package com.example.swiggy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.swiggy.entity.Customer;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {
	
	@Query("""
		       SELECT DISTINCT c
		       FROM Customer c
		       LEFT JOIN FETCH c.orders
		       """)
	
	
		List<Customer> findAllCustomersWithOrders();

}