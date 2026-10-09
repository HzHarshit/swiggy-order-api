package com.example.swiggy.response;

import com.example.swiggy.enums.OrderStatus;

public class OrderResponse {

    private Long id;
    
    private Long customerId;
    private Long restaurantId;
    
    private String foodName;
    private int quantity;
    private double price;
    private OrderStatus status;

    public OrderResponse() {

    }

    public OrderResponse(
            Long id,
            Long customerId,
            Long restaurantId,
            String foodName,
            int quantity,
            double price,
            OrderStatus status) {

        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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