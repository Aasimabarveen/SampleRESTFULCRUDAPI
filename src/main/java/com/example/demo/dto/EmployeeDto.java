package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDto {
	@NotNull
	private long id;
	@NotEmpty(message = "Please Provide First Name")
	@Size(min = 3, message = "Please Provide Valid First Name")
	private String firstName;
	@NotEmpty(message = "Please Provide Last Name")
	private String lastName;
	@NotEmpty(message = "Please Provide Email Id")
	@Email(message = "Please Provide Valid Email Id")
	private String email;
}
