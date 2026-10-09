package com.example.swiggy.service;

import java.util.List;

import com.example.swiggy.entity.Restaurant;
import com.example.swiggy.request.RestaurantRequest;
import com.example.swiggy.response.RestaurantResponse;

public interface RestaurantService {

	RestaurantResponse createRestaurant (RestaurantRequest request);
	
	List<RestaurantResponse> getAllRestaurant();
	
	RestaurantResponse getRestaurantById(Long id);
	
	RestaurantResponse  updateRestaurant(Long id , RestaurantRequest restaurantResquest);
	
	void deleteRestaurant(Long id);
	
	
}
