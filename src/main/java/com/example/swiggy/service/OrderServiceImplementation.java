package com.example.swiggy.service;

import java.util.List;
import org.slf4j.Logger;
import java.math.BigDecimal;

import com.example.swiggy.event.OrderCreatedEvent;
import com.example.swiggy.kafka.OrderKafkaProducer;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.swiggy.entity.OutboxEvent;
import com.example.swiggy.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import com.example.swiggy.entity.Customer;
import com.example.swiggy.entity.Order;
import com.example.swiggy.entity.Restaurant;
import com.example.swiggy.enums.OrderStatus;
import com.example.swiggy.exception.OrderNotFoundException;
import com.example.swiggy.repository.CustomerRepository;
import com.example.swiggy.repository.OrderRepository;
import com.example.swiggy.repository.RestaurantRepository;
import com.example.swiggy.request.OrderRequest;
import com.example.swiggy.response.OrderResponse;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImplementation implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImplementation.class);

	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;

	private final RestaurantRepository restaurantRepository;

	private final OrderKafkaProducer orderKafkaProducer;

	private final OutboxEventRepository outboxEventRepository;
	private final ObjectMapper objectMapper;

	public OrderServiceImplementation(OrderRepository orderRepository, CustomerRepository customerRepository,
			RestaurantRepository restaurantRepository, OrderKafkaProducer orderKafkaProducer,
			OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {

		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.restaurantRepository = restaurantRepository;
		this.orderKafkaProducer = orderKafkaProducer;
		this.outboxEventRepository = outboxEventRepository;
		this.objectMapper = objectMapper;
	}

	// CREATE ORDER
	@Transactional
	@Override
	public OrderResponse createOrder(OrderRequest orderRequest) {

		logger.info("Creating order for customerId: {} and restaurantId: {}", orderRequest.getCustomerId(),
				orderRequest.getRestaurantId());

		// Find customer using customerId
		Customer customer = customerRepository.findById(orderRequest.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found with id: " + orderRequest.getCustomerId()));

		// Find Restaurant using RestaurantId
		Restaurant restaurant = restaurantRepository.findById(orderRequest.getRestaurantId()).orElseThrow(
				() -> new RuntimeException("Restaurant not found with id: " + orderRequest.getRestaurantId()));

		// Create new Order
		Order order = new Order();

		// Connect Order with Customer and Restaurant
		order.setCustomer(customer);
		order.setRestaurant(restaurant);

		order.setFoodName(orderRequest.getFoodName());
		order.setPrice(orderRequest.getPrice());
		order.setQuantity(orderRequest.getQuantity());

		// Default status
		order.setStatus(OrderStatus.PLACED);

		// Save Order

		// Save Order
		Order orderSave = orderRepository.save(order);

		// Create Kafka event using the saved order
		OrderCreatedEvent event = new OrderCreatedEvent(orderSave.getId(), orderSave.getFoodName(),
				orderSave.getQuantity(), BigDecimal.valueOf(orderSave.getPrice()), orderSave.getStatus().name());

		// Publish the event only after the database transaction commits

try {
    String payload = objectMapper.writeValueAsString(event);

    OutboxEvent outboxEvent = new OutboxEvent(
            "ORDER",
            orderSave.getId(),
            "ORDER_CREATED",
            payload,
            "PENDING"
    );

    outboxEventRepository.save(outboxEvent);

    logger.info(
            "Order-created event saved to outbox for orderId: {}",
            orderSave.getId()
    );

} catch (JsonProcessingException exception) {
    throw new IllegalStateException(
            "Failed to serialize order-created event",
            exception
    );
}


		logger.info("Order created successfully with id: {}", orderSave.getId());

		return convertToResponse(orderSave);

	}

	// GET ALL ORDERS - PAGINATION
	@Override
	public Page<OrderResponse> getAllOrders(Pageable pageable) {

		Page<Order> orders = orderRepository.findAll(pageable);

		return orders.map(this::convertToResponse);
	}

	// GET ORDER BY ID
	@Override
	public OrderResponse getOrderById(Long id) {

		logger.info("Fetching order with id: {}", id);

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

		return convertToResponse(order);
	}

	// UPDATE ORDER
	@Override
	public OrderResponse updateOrder(Long id, OrderRequest request) {

		logger.info("Updating order with id: {}", id);

		// Find existing order
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

		// Find new/existing customer
		Customer customer = customerRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));

		Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
				.orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + request.getRestaurantId()));

		// Update customer relationship
		order.setCustomer(customer);
		order.setRestaurant(restaurant);

		// Update order details
		order.setFoodName(request.getFoodName());
		order.setQuantity(request.getQuantity());
		order.setPrice(request.getPrice());

		// Save updated order
		Order updatedOrder = orderRepository.save(order);

		logger.info("Order updated successfully with id: {}", id);

		return convertToResponse(updatedOrder);
	}

	// DELETE ORDER
	@Override
	public void deleteOrder(Long id) {

		logger.info("Deleting order with id: {}", id);

		if (!orderRepository.existsById(id)) {

			throw new RuntimeException("Order not found with id: " + id);
		}

		orderRepository.deleteById(id);

		logger.info("Order deleted successfully with id: {}", id);
	}

	// GET ORDERS BY STATUS
	@Override
	public List<OrderResponse> getOrdersByStatus(OrderStatus status) {

		List<Order> orders = orderRepository.findByStatus(status);

		return orders.stream().map(this::convertToResponse).toList();
	}

	// GET ORDERS BY CUSTOMER NAME
	// This method will be changed in Step 3F/relationship cleanup
	@Override
	public List<OrderResponse> getOrdersByCustomerId(Long customerId) {

		List<Order> orders = orderRepository.findByCustomerId(customerId);

		return orders.stream().map(this::convertToResponse).toList();
	}

	// CONVERT ENTITY TO RESPONSE
	public OrderResponse convertToResponse(Order order) {

		return new OrderResponse(order.getId(), order.getCustomer().getId(), order.getRestaurant().getId(),
				order.getFoodName(), order.getQuantity(), order.getPrice(), order.getStatus());
	}
}