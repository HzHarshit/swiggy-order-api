package com.example.swiggy.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class OrderRequest {

	@Positive(message = "Customer ID must be greater than 0")
	private Long customerId;
	
	@Positive(message = "Restaurant ID must be greater than 0")
	private Long restaurantId;
	
	@NotBlank(message = "Food name cannot be empty")
	private String foodName;
	
	@Min(value = 1 , message = "Quantity must be atleast 1")
	private int quantity;
	
	@Positive(message = "Price must be greater than 0")
	private double price ;

	
	public OrderRequest() {
		
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getRestaurantId() {
	    return restaurantId;
	}
	
	public void setRestaurantId(Long restaurantId) {
	    this.restaurantId = restaurantId;
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


	
}
