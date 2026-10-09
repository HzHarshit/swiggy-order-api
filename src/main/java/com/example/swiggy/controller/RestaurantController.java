package com.example.swiggy.controller;

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

import com.example.swiggy.request.CustomerRequest;
import com.example.swiggy.request.RestaurantRequest;
import com.example.swiggy.response.CustomerResponse;
import com.example.swiggy.response.RestaurantResponse;
import com.example.swiggy.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService ;
	}
	
	//Create
	@PostMapping
	public ResponseEntity<RestaurantResponse> createRestaurant (
			@Valid @RequestBody RestaurantRequest request) {
		
		RestaurantResponse response = restaurantService.createRestaurant(request);
		
		return new ResponseEntity<>(response , HttpStatus.CREATED);
		
	}
	
	//Get All
	@GetMapping
	public ResponseEntity<List<RestaurantResponse>> getAllRestaurant(){
		return ResponseEntity.ok(restaurantService.getAllRestaurant()) ;
	}
	
	//Get by Id
	@GetMapping("/{id}")
	public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
		return ResponseEntity.ok(restaurantService.getRestaurantById(id));
	}
	
	//Update
	@PutMapping("/{id}")
	public ResponseEntity<RestaurantResponse>  updateRestaurant(
			@PathVariable Long id ,
			@Valid @RequestBody RestaurantRequest restaurantResquest){
		return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantResquest));
	}
	
	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
		restaurantService.deleteRestaurant(id);
		
		return ResponseEntity.ok("Restaurant deleted Successfully");
	}
	
	
	
	
	
	
	

}
