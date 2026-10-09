package com.example.swiggy.exception;

public class OrderNotFoundException extends RuntimeException{

	public OrderNotFoundException(String message) {
		super(message);
	}
}
