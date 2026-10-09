package com.example.swiggy.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequest {

	@NotBlank(message = "Name cannot be empty")
	public String name;
	
	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Invalid email format")
	public String email;
	
	@NotBlank(message = "Phone number cannot be empty")
	public String phone;
	
	@NotBlank(message = "Address cannot be empty")
	public String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
