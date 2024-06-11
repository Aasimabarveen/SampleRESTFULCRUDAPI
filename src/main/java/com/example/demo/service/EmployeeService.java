package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	List<EmployeeDto> getAllEmployee();

	EmployeeDto getById(long id);

	EmployeeDto updateEmployee(EmployeeDto employeeDto, long id);

	void deleteEmployee(long id);

	Employee getByFirstName(String name);
}
