package com.example.demo.controller;

import java.util.List;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	// Rest Api to Create an Employee, getting the employee Json obj in requestBody
	// from client,Here im using reponseEntity<T> as Return type which helps to pack
	// employee obj and httpstatu code along with http header. instead of jst the
	// employee obj
	@PostMapping()
	public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto emp) {
		return new ResponseEntity<EmployeeDto>(employeeService.saveEmployee(emp), HttpStatus.CREATED);
	}

	// Rest Api to Get an Employee by id, getting the employee id in URL from client
	@GetMapping()
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployee();
	}

	// Rest Api to Get all Employee
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable(name = "id") long id) {
		return new ResponseEntity<EmployeeDto>(employeeService.getById(id), HttpStatus.OK);
	}

	// REst Api to Update an Employee DEtails, Getting the id from URL and Employee
	// Updated Json data from client REquestBody
	@PutMapping("{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") long id,
			@Valid @RequestBody EmployeeDto employeedto) {
		return new ResponseEntity<EmployeeDto>(employeeService.updateEmployee(employeedto, id), HttpStatus.OK);

	}

	// Rest Api to DElete a Employee by id.
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<String>("Employee Deleted Sucessfully..", HttpStatus.OK);

	}
}
