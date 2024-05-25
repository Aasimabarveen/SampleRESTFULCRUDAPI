package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	List<Employee> getAllEmployee();

	EmployeeDto getById(long id);

	EmployeeDto updateEmployee(EmployeeDto employeeDto, long id);

	void deleteEmployee(long id);
}
