package com.alp.mic.springmicroservicedemo.service;

import com.alp.mic.springmicroservicedemo.mapper.EmployeeMapper;
import com.alp.mic.springmicroservicedemo.model.Employee;
import com.alp.mic.springmicroservicedemo.repository.EmployeeRepository;
import com.alp.mic.springmicroservicedemo.response.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse getEmployeeDetails(Integer id) {
        System.out.println("EmployeeController.getEmployeeDetails: " + id);

        return employeeRepository.findById(id)
                .map(employeeMapper::toResponse)
                .orElse(null);
    }

    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse saveEmployee(Employee employee) {
        System.out.println("EmployeeService.saveEmployee: " + employee);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }
}
