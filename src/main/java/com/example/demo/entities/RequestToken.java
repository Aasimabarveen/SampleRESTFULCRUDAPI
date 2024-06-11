package com.example.demo.entities;

import java.time.Instant;
import jakarta.validation.constraints.NotEmpty;


import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestToken {
	@NotEmpty(message = "Please Provide Token")
	@Size(min = 3, message = "Please Provide Valid Token")
	String  token;
}
