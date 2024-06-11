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

import com.example.demo.entities.RefreshToken;
import com.example.demo.entities.Request;
import com.example.demo.entities.RequestToken;
import com.example.demo.entities.Response;
import com.example.demo.serviceImpl.RefreshTokenService;
import com.example.demo.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private RefreshTokenService refreshTokenService;

	@PostMapping("/login")
	public ResponseEntity<Response> AuthenticateUser(@Valid @RequestBody Request login) {
		System.out.println("In Controller got userName " + login.getUserName());
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		System.out.println("Authenticates Sucessfully!!");
		String accessToken = util.generateToken(login.getUserName());
		String refreshToken = refreshTokenService.createRefreshToken(login.getUserName()).getRefreshToken();
		return new ResponseEntity<Response>(new Response(accessToken,refreshToken), HttpStatus.OK);

	}
	@PostMapping("/refreshToken")
	public ResponseEntity<Response> refreshToken(@Valid @RequestBody RequestToken requestToken){
		RefreshToken refreshToken = refreshTokenService.findByToken(requestToken.getToken());
		refreshTokenService.isValid(refreshToken);
		String accesstoken=util.generateToken(refreshToken.getUserName());
					
		return new ResponseEntity<Response>(new Response(accesstoken,requestToken.getToken()), HttpStatus.OK);	
	} 
	
}
