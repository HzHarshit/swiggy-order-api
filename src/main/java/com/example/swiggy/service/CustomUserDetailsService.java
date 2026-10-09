package com.example.swiggy.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.swiggy.entity.User;
import com.example.swiggy.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	//constructor Injection
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws 
	UsernameNotFoundException {
		User user = userRepository
		.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username : " + username 
						)
				);
		
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
		
	}
	

}
