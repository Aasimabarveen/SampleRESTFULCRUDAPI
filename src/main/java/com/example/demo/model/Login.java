package com.example.demo.model;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	@NotEmpty(message = "Please Provide User Name")
	@Size(min = 3, message = "Please Provide Valid User Name")
	String userName;
	@NotEmpty(message = "Please Provide Password")
	@Size(min = 3, message = "Please Provide Valid Password")
	String password;

}
