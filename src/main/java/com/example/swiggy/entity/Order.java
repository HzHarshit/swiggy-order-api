package com.example.swiggy.entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;



import com.example.swiggy.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
	    name = "orders",
	    indexes = {
	        @Index(
	            name = "idx_orders_customer_id",
	            columnList = "customer_id"
	        )
	    }
	)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	private String foodName;
	private int quantity;
	private double price;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	public Order() {
		
	}
	
	public Order(long id , Customer customerName , String foodName , int quantity ,double price , OrderStatus status) {
		this.id = id;
		this.customer =customerName;
		this.foodName = foodName;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Restaurant getRestaurant() {
	    return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
	    this.restaurant = restaurant;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
    
	
}
