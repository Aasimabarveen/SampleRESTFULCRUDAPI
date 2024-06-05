package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.serviceImpl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	EmployeeServiceImpl service;

	@Autowired
	MockMvc mockMvc;

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
	void testSaveEmployee_Success() throws Exception {

		when(service.saveEmployee(ArgumentMatchers.any())).thenReturn(employeeDto);

		// Converting Java objects into JSON, send it to cilent via API (Immitate the
		// api calls wiht json data)
		ObjectMapper objMapper = new ObjectMapper();
		String dtoToString = objMapper.writeValueAsString(employeeDto);

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(dtoToString))
				.andExpect(status().isCreated());
	}

	@Test
	void testSaveEmployee_Faliure() throws Exception {

		employeeDto.setFirstName("");
		ObjectMapper objMapper = new ObjectMapper();
		String employeeDtoJson = objMapper.writeValueAsString(employeeDto);

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(employeeDtoJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetAllEmployee() throws Exception {
		when(service.getAllEmployee()).thenReturn(employeeListDto);

		mockMvc.perform(get("/api/employees").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetEmployeeId_Failure() throws Exception {

		when(service.getById(0)).thenThrow(new ResourceNotFoundException("Employee Not Found with Id "));

		mockMvc.perform(get("/api/employees/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	void testGetEmployeeId_Success() throws Exception {
		when(service.getById(0L)).thenReturn(employeeDto2);

		mockMvc.perform(get("/api/employees/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void testUpdateEmployee_Success() throws Exception {
		when(service.updateEmployee(ArgumentMatchers.any(), ArgumentMatchers.eq(2L))).thenReturn(employeeDto2);

		mockMvc.perform(put("/api/employees/2").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(employeeDto2))).andExpect(status().isOk());

	}

	@Test
	void testUpdateEmployee_FailureDtoError() throws Exception {

		employeeDto2.setFirstName("");
		mockMvc.perform(put("/api/employees/2").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(employeeDto2))).andExpect(status().isBadRequest());

	}

	@Test
	void testUpdateEmployee_FailureIdNotFound() throws Exception {

		when(service.updateEmployee(ArgumentMatchers.any(), ArgumentMatchers.eq(0L)))
				.thenThrow(new ResourceNotFoundException("Employee Id Not Found"));

		mockMvc.perform(put("/api/employees/0").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(employeeDto2))).andExpect(status().isNotFound());

	}

	@Test
	void testDeleteEmployee_Success() throws Exception {
		doNothing().when(service).deleteEmployee(2);

		mockMvc.perform(delete("/api/employees/2")).andExpect(status().isOk());
	}

	@Test
	void testDeleteEmployee_Failure() throws Exception {
		 doThrow(new ResourceNotFoundException("Employee Not Found with Id 2")).when(service).deleteEmployee(2);

		mockMvc.perform(delete("/api/employees/2")).andExpect(status().isNotFound());
	}

}
