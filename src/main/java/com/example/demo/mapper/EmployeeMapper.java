package com.example.demo.mapper;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;

public class EmployeeMapper {

	public static EmployeeDto toEmployeeDto(Employee employee) {
		 if (employee == null) {
	            return null;
	        }
		EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
				employee.getEmail());
		return employeeDto;
	}

	public static Employee toEmployee(EmployeeDto employeeDto) {
		 if (employeeDto == null) {
	            return null;
	        }
		Employee employee = new Employee(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail());
		return employee;
	}
}
