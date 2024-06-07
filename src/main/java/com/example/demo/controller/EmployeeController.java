package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.example.demo.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Employee", description = "Employee Crud API")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	public static final Logger logger = LogManager.getLogger(EmployeeController.class);
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	// Rest Api to Create an Employee, getting the employee Json obj in requestBody
	// from client,Here im using reponseEntity<T> as Return type which helps to pack
	// employee obj and httpstatu code along with http header. instead of jst the
	// employee obj
	@Operation(summary = "save Employee", description = "Save Employee data to datasource")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request") })
	@PostMapping()
	public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto emp) {
		logger.info("Request received to save employee: {}", emp);
		EmployeeDto savedEmployee = employeeService.saveEmployee(emp);
		logger.info("Employee saved successfully with ID: {}", savedEmployee.getId());
		return new ResponseEntity<EmployeeDto>(savedEmployee, HttpStatus.CREATED);
	}

	// Rest Api to Get an Employee by id, getting the employee id in URL from client
	@Operation(summary = "Fetch All Employees", description = "Fetch all the employees from datasource ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Fetched Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request") })
	@GetMapping()
	public List<EmployeeDto> getAllEmployee() {
		logger.info("Request Received to Get All Employees: ");
		return employeeService.getAllEmployee();
	}

	// Rest Api to Get all Employee
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable(name = "id") long id) {
		logger.info("Request Received to get Employee By Id ", id);
		return new ResponseEntity<EmployeeDto>(employeeService.getById(id), HttpStatus.OK);
	}

	// REst Api to Update an Employee DEtails, Getting the id from URL and Employee
	// Updated Json data from client REquestBody
	@PutMapping("{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") long id,
			@Valid @RequestBody EmployeeDto employeedto) {
		logger.info("Request Received to update Employee ", employeedto);
		return new ResponseEntity<EmployeeDto>(employeeService.updateEmployee(employeedto, id), HttpStatus.OK);

	}

	// Rest Api to DElete a Employee by id.
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		logger.info("Request Received to Delete Employee ", id);
		employeeService.deleteEmployee(id);
		logger.info("Employee deleted with ID: {}", id);
		return new ResponseEntity<String>("Employee Deleted Sucessfully..", HttpStatus.OK);

	}
}
