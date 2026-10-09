package com.example.swiggy.response;

public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private String cuisine;
    private double rating;
    
    public RestaurantResponse() {

    }

    public RestaurantResponse(Long id,
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

}
