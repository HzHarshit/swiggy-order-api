package com.example.swiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.swiggy.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

}
