
package com.example.swiggy.event;

import java.math.BigDecimal;

public class OrderCreatedEvent {

    private Long orderId;
    private String foodName;
    private int quantity;
    private BigDecimal price;
    private String status;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(
            Long orderId,
            String foodName,
            int quantity,
            BigDecimal price,
            String status) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
