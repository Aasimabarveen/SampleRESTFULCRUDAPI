package com.example.demo.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	EmployeeRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<Employee> employee= repository.findByfirstName(username);
		if(employee.get()==null)
				throw new ResourceNotFoundException("User Not Found");  
		UserDetails user= User.builder().username(employee.get().getFirstName()).password(employee.get().getEmail()).build();
		return user;
	}

}
