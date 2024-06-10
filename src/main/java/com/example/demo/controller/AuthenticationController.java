package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Login;
import com.example.demo.model.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class AuthenticationController {

	 @Autowired
	 private AuthenticationManager authenticationManager;
	
	@PostMapping()
	public ResponseEntity<LoginResponse> AuthenticateUser(@Valid @RequestBody Login login) {
		System.out.println("In Controller got userName "+ login.getUserName());
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		System.out.println("Authenticates Sucessfully!!");  
		return new ResponseEntity<LoginResponse>(new LoginResponse(login.getUserName()), HttpStatus.OK);

	}

}
