package com.example.swiggy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import java.util.List;
import jakarta.persistence.OneToMany;

import com.example.swiggy.entity.Order;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private String cuisine;

    private double rating;
    
    @OneToMany(mappedBy = "restaurant" , fetch = FetchType.LAZY)
    private List<Order> orders;

    public Restaurant() {

    }

    public Restaurant(Long id,
                      String name,
                      String location,
                      String cuisine,
                      double rating) {

        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}