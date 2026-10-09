package com.example.swiggy.controller;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import com.example.swiggy.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.swiggy.enums.OrderStatus;
import com.example.swiggy.repository.OrderRepository;
import com.example.swiggy.request.OrderRequest;
import com.example.swiggy.response.OrderResponse;
import com.example.swiggy.service.OrderService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	
	//Constructor Injection
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	//create Order
	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
	        @Valid @RequestBody OrderRequest orderRequest) {

	    OrderResponse orderResponse =
	            orderService.createOrder(orderRequest);

	    ApiResponse<OrderResponse> response =
	            new ApiResponse<>(
	                    true,
	                    "Order created successfully",
	                    orderResponse,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.status(201).body(response);
	}
	
	//Get All Orders
	@GetMapping
	public ResponseEntity<ApiResponse<Page<OrderResponse>>> getAllOrders(
	        Pageable pageable) {

	    Page<OrderResponse> orders =
	            orderService.getAllOrders(pageable);

	    ApiResponse<Page<OrderResponse>> response =
	            new ApiResponse<>(
	                    true,
	                    "Orders fetched successfully",
	                    orders,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.ok(response);
	}
	
	
	//Get Order by Status
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByStatus(
	        @PathVariable OrderStatus status) {

	    List<OrderResponse> orders =
	            orderService.getOrdersByStatus(status);

	    ApiResponse<List<OrderResponse>> response =
	            new ApiResponse<>(
	                    true,
	                    "Orders fetched by status successfully",
	                    orders,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.ok(response);
	}
	
	//Get order by customer Id
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByCustomerId(
	        @PathVariable Long customerId) {

	    List<OrderResponse> orders =
	            orderService.getOrdersByCustomerId(customerId);

	    ApiResponse<List<OrderResponse>> response =
	            new ApiResponse<>(
	                    true,
	                    "Customer orders fetched successfully",
	                    orders,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.ok(response);
	}
	
	//Get Order By Id
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
	        @PathVariable Long id) {

	    OrderResponse orderResponse =
	            orderService.getOrderById(id);

	    ApiResponse<OrderResponse> response =
	            new ApiResponse<>(
	                    true,
	                    "Order fetched successfully",
	                    orderResponse,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.ok(response);
	}

	//Update Order
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
	        @PathVariable Long id,
	        @Valid @RequestBody OrderRequest request) {

	    OrderResponse orderResponse =
	            orderService.updateOrder(id, request);

	    ApiResponse<OrderResponse> response =
	            new ApiResponse<>(
	                    true,
	                    "Order updated successfully",
	                    orderResponse,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.ok(response);
	}
	
	//DeleteOrder
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteOrder(
	        @PathVariable Long id) {

	    orderService.deleteOrder(id);

	    ApiResponse<Void> response =
	            new ApiResponse<>(
	                    true,
	                    "Order deleted successfully",
	                    null,
	                    LocalDateTime.now()
	            );

	    return ResponseEntity.ok(response);
	}

}


















