package com.example.demo.serviceImpl;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Employee employee = EmployeeMapper.toEmployee(employeeDto);
		return EmployeeMapper.toEmployeeDto(employeeRepository.save(employee));
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

	@Override
	public EmployeeDto getById(long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent())
			return EmployeeMapper.toEmployeeDto(employee.get());
		else
			throw new ResourceNotFoundException("Employee Not Found with Id " + id);

	}

	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
		Optional<Employee> oldEmployee = employeeRepository.findById(id);
		if (oldEmployee == null)
			throw new ResourceNotFoundException("Employee Not Found with Id " + id);

		oldEmployee.get().setFirstName(employeeDto.getFirstName());
		oldEmployee.get().setLastName(employeeDto.getLastName());
		oldEmployee.get().setEmail(employeeDto.getEmail());
		employeeRepository.save(oldEmployee.get());
		return EmployeeMapper.toEmployeeDto(oldEmployee.get());
	}

	@Override
	public void deleteEmployee(long id) {
		EmployeeDto employeeDto = getById(id);
		if (employeeDto != null) {
			employeeRepository.deleteById(id);
		} else
			throw new ResourceNotFoundException("Employee Not Found with Id " + id);

	}

}
