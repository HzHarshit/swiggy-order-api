package com.example.swiggy.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.swiggy.enums.OrderStatus;
import com.example.swiggy.request.OrderRequest;
import com.example.swiggy.response.OrderResponse;

public interface OrderService {

	OrderResponse createOrder(OrderRequest orderRequest);

	Page<OrderResponse> getAllOrders(Pageable pageable);
  
	OrderResponse getOrderById(Long id);
	
	OrderResponse updateOrder(Long id , OrderRequest orderRequest );
	
	void deleteOrder(Long id);
	
	List<OrderResponse> getOrdersByStatus(OrderStatus status);

	List<OrderResponse> getOrdersByCustomerId(Long customerId);}
