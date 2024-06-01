package com.example.demo.serviceImpl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.*;
import com.example.demo.exception.ResourceNotFoundException;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl service;

	@Mock
	EmployeeRepository employeeRepository;

	EmployeeDto employeeDto;
	Employee employee;
	List<Employee> employeeList;
	List<EmployeeDto> employeeListDto;
	Employee employee2;
	EmployeeDto employeeDto2;

	@BeforeEach
	void setUp() throws Exception {
		employeeList = new ArrayList<>();
		employeeListDto = new ArrayList<>();
		Employee employee1 = new Employee(1L, "John", "Doe", "john.doe@example.com");
		employee2 = new Employee(2L, "Jane", "Doe", "jane.doe@example.com");
		employeeList.add(employee1);
		employeeList.add(employee2);
		EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Doe", "john.doe@example.com");
		employeeDto2 = new EmployeeDto(2L, "Jane", "Doe", "jane.doe@example.com");
		employeeListDto.add(employeeDto1);
		employeeListDto.add(employeeDto2);

		employee = new Employee(1L, "John", "Doe", "john.doe@example.com");
		employeeDto = new EmployeeDto(1L, "John", "Doe", "john.doe@example.com");

	}

	@Test
	void testSaveEmployee_success() {

		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		EmployeeDto employeePersisted = service.saveEmployee(employeeDto);

		assertEquals(employeeDto.getId(), employeePersisted.getId());
		assertEquals(employeeDto.getEmail(), employeePersisted.getEmail());
		assertEquals(employeeDto.getFirstName(), employeePersisted.getFirstName());
		assertEquals(employeeDto.getLastName(), employeePersisted.getLastName());
	}

	@Test
	void testSaveEmployee_Fail_Null() {

		EmployeeDto employeePersisted = service.saveEmployee(null);

		assertNull(employeePersisted);
	}

	@Test
	void testGetAllEmployee() {
		when(employeeRepository.findAll()).thenReturn(employeeList);
		List<EmployeeDto> employeeDtoPersistedList = service.getAllEmployee();
		assertIterableEquals(employeeListDto, employeeDtoPersistedList);
	}

	@Test
	void testGetById_Success() {
		when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));
		EmployeeDto employeeDto = service.getById(2L);
		assertEquals(employeeDto2.getId(), employeeDto.getId());
		assertEquals(employeeDto2.getEmail(), employeeDto.getEmail());
		assertEquals(employeeDto2.getFirstName(), employeeDto.getFirstName());
		assertEquals(employeeDto2.getLastName(), employeeDto.getLastName());
	}

	@Test
	void testGetById_Fail_ResourceNotFound() {
		when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			service.getById(2);
		});
	}

	@Test
	void testUpdateEmployee_success() {
		when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));
		EmployeeDto updatedEmployeeDto = new EmployeeDto(2L, "Jane", "smith", "jane.doe@example.com");

		when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);
		EmployeeDto result = service.updateEmployee(updatedEmployeeDto, 2L);

		assertEquals(updatedEmployeeDto.getFirstName(), result.getFirstName());
		assertEquals(updatedEmployeeDto.getLastName(), result.getLastName());
		assertEquals(updatedEmployeeDto.getEmail(), result.getEmail());
	}

	@Test
	void testUpdateEmployee_fail_ResourceNotFound() {
		when(employeeRepository.findById(2L)).thenReturn(Optional.empty());
		EmployeeDto updatedEmployeeDto = new EmployeeDto(2L, "Jane", "smith", "jane.doe@example.com");

		assertThrows(ResourceNotFoundException.class, () -> {
			service.updateEmployee(updatedEmployeeDto, 2L);
		});
	}

	@Test
	void testDeleteEmployee_success() {
		when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));
		service.deleteEmployee(2L);
		verify(employeeRepository).findById(2L);
		verify(employeeRepository).deleteById(2L);
	}

	@Test
	void testDeleteEmployee_fail_resourceNotFound() {
		when(employeeRepository.findById(2L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteEmployee(2L);
		});
		verify(employeeRepository).findById(2L);
	}

}
