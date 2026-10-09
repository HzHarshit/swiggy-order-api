package com.example.swiggy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.swiggy.entity.Order;
import com.example.swiggy.enums.OrderStatus;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByStatus(OrderStatus status);
	
	List<Order> findByCustomerId(Long customerId);	
	@Query("""
		       SELECT o
		       FROM Order o
		       WHERE o.status = :status
		       """)
		List<Order> findOrdersByStatus(
		        @Param("status") String status);

}
