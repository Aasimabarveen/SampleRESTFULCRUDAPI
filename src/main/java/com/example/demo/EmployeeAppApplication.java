package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeAppApplication {
//http://localhost:8080/swagger-ui/index.html - url for Swagger Ui
//http://localhost:8080/v3/api-docs - docs in json format	
	/*
	 * "userName":"asi", "password":"hhhhh"
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeAppApplication.class, args);

	}

}
