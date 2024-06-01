package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;

class EmployeeMapperTest {

	Employee employee1;
	EmployeeDto employeeDto1;
	EmployeeDto employeeDto2;
	Employee employee2;

	@BeforeEach
	public void setUp() {
		employee1 = new Employee(1, "Sana", "Zakiyah", "sana@gmail.com");
		employeeDto2 = new EmployeeDto(2, "Aasima", "Barveen", "barveen@gmail.com");
	}

	@Test
	public void toEmployeeDtoTest() {
		employeeDto1 = EmployeeMapper.toEmployeeDto(employee1);
		assertEquals(employee1.getId(), employeeDto1.getId());
		assertEquals(employee1.getEmail(), employeeDto1.getEmail());
		assertEquals(employee1.getFirstName(), employeeDto1.getFirstName());
		assertEquals(employee1.getLastName(), employeeDto1.getLastName());
	}

	@Test
	public void toEmployeeDtoNullTest() {

		assertNull(EmployeeMapper.toEmployeeDto(null));

	}

	@Test
	public void toEmployeeTest() {
		employee2 = EmployeeMapper.toEmployee(employeeDto2);
		assertEquals(employeeDto2.getId(), employee2.getId());
		assertEquals(employeeDto2.getEmail(), employee2.getEmail());
		assertEquals(employeeDto2.getFirstName(), employee2.getFirstName());
		assertEquals(employeeDto2.getLastName(), employee2.getLastName());
	}

	@Test
	public void toEmployeeNullTest() {

		assertNull(EmployeeMapper.toEmployee(null));

	}

}
