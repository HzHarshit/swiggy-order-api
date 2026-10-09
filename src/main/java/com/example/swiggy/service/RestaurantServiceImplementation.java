package com.example.swiggy.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.swiggy.entity.Restaurant;
import com.example.swiggy.repository.RestaurantRepository;
import com.example.swiggy.request.RestaurantRequest;
import com.example.swiggy.response.RestaurantResponse;

@Service
public class RestaurantServiceImplementation implements RestaurantService{
	
	private final RestaurantRepository restaurantRepository;
	
	//Constructor Injection
	
	public RestaurantServiceImplementation(RestaurantRepository restaurantRepository) {
	
		this.restaurantRepository = restaurantRepository;
	}
	
	
	//Create
    @Override
    public RestaurantResponse createRestaurant(
            RestaurantRequest request) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setLocation(request.getLocation());
        restaurant.setCuisine(request.getCuisine());
        restaurant.setRating(request.getRating());

        Restaurant savedRestaurant =
                restaurantRepository.save(restaurant);

        return convertToResponse(savedRestaurant);
    }
	
	@Override
	public List<RestaurantResponse> getAllRestaurant(){
		List<Restaurant> restaurant = restaurantRepository.findAll();
		return restaurant.stream().map(this::convertToResponse).toList();
	}
	
	@Override
	public RestaurantResponse getRestaurantById(Long id) {
		
		Restaurant restaurant = restaurantRepository.findById(id).
				orElseThrow(()-> new RuntimeException(
						"Restaurant Not Found with Id : " + id)
						);
		
		return convertToResponse(restaurant);
	}
	
	
	//Update
	@Override
	public RestaurantResponse  updateRestaurant(Long id , RestaurantRequest restaurantResquest) {
		
		Restaurant restaurant = restaurantRepository.findById(id).
				orElseThrow(()-> new RuntimeException(
						"Restaurant not found with id " + id)
						);
		restaurant.setName(restaurantResquest.getName());
        restaurant.setLocation(restaurantResquest.getLocation());
        restaurant.setCuisine(restaurantResquest.getCuisine());
        restaurant.setRating(restaurantResquest.getRating());
        
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return convertToResponse(updatedRestaurant);
		
	}
	
	@Override
	public void deleteRestaurant(Long id) {
		if(!restaurantRepository.existsById(id)) {
			throw new RuntimeException( "Restaurant not found with Id : "+ id);
		}
		
		restaurantRepository.deleteById(id);
	}
	
	
	//Entity -> Response
    // ENTITY → RESPONSE
    private RestaurantResponse convertToResponse(
            Restaurant restaurant) {

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getCuisine(),
                restaurant.getRating()
        );
	}

}
