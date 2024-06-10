package com.example.demo.serviceImpl;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		if (employeeDto == null) {
			logger.warn("Attempted to save a null EmployeeDto.");
			return null;
		}

		Employee employee = EmployeeMapper.toEmployee(employeeDto);
		Employee savedEmployee = employeeRepository.save(employee);
		logger.info("Employee saved with ID: {}", savedEmployee.getId());
		return EmployeeMapper.toEmployeeDto(savedEmployee);
	}

	@Override
	public List<EmployeeDto> getAllEmployee() {
		logger.info("Fetching all employees.");
		List<EmployeeDto> employeeDto = new ArrayList<>();
		for (Employee emp : employeeRepository.findAll())
			employeeDto.add(EmployeeMapper.toEmployeeDto(emp));
		logger.debug("Total employees fetched: {}", employeeDto.size());
		return employeeDto;
	}

	@Override
	public EmployeeDto getById(long id) {
		logger.info("Fetching employee by ID: {}", id);
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			logger.debug("Employee found with ID: {}", id);
			return EmployeeMapper.toEmployeeDto(employee.get());
		} else {
			logger.error("Employee not found with ID: {}", id);
			throw new ResourceNotFoundException("Employee Not Found with Id " + id);
		}

	}

	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
		logger.info("Updating employee with ID: {}", id);
		Optional<Employee> oldEmployee = employeeRepository.findById(id);
		if (!oldEmployee.isPresent()) {
			logger.error("Cannot update. Employee not found with ID: {}", id);
			throw new ResourceNotFoundException("Employee Not Found with Id " + id);
		}
		oldEmployee.get().setFirstName(employeeDto.getFirstName());
		oldEmployee.get().setLastName(employeeDto.getLastName());
		oldEmployee.get().setEmail(employeeDto.getEmail());
		Employee updatedEmployee = employeeRepository.save(oldEmployee.get());
		logger.info("Employee updated with ID: {}", updatedEmployee.getId());
		return EmployeeMapper.toEmployeeDto(oldEmployee.get());
	}

	@Override
	public void deleteEmployee(long id) {
		logger.info("Deleting employee with ID: {}", id);
		EmployeeDto employeeDto = getById(id);
		employeeRepository.deleteById(id);
		logger.info("Employee deleted with ID: {}", id);
	}

	@Override
	public Employee getByFirstName(String name) {
		logger.info("Fetching employee by Name: {}", name);
		Optional<Employee> user = employeeRepository.findByfirstName(name);
		if (user.isPresent()) {
			logger.debug("Employee found with Name: {}", name);
			return user.get();
		} else
		{
			logger.error("Employee not found with Name: {}", name);
			throw new ResourceNotFoundException("Employee Not Found with name " + name);
		}
			
	}

}
