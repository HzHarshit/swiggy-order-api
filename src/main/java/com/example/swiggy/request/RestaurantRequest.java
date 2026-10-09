package com.example.swiggy.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class RestaurantRequest {

    @NotBlank(message = "Restaurant name cannot be empty")
    private String name;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotBlank(message = "Cuisine cannot be empty")
    private String cuisine;

    @DecimalMin(
            value = "0.0",
            message = "Rating cannot be less than 0"
    )
    @DecimalMax(
            value = "5.0",
            message = "Rating cannot be greater than 5"
    )
    private double rating;

    public RestaurantRequest() {

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
}