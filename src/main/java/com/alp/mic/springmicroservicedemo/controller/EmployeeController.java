package com.alp.mic.springmicroservicedemo.controller;

import com.alp.mic.springmicroservicedemo.model.Employee;
import com.alp.mic.springmicroservicedemo.repository.EmployeeRepository;
import com.alp.mic.springmicroservicedemo.response.EmployeeResponse;
import com.alp.mic.springmicroservicedemo.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeDetails(@PathVariable("id") Integer id) {
        System.out.println("EmployeeController.getEmployeeDetails: " + id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<EmployeeResponse> employeeResponse = Optional.ofNullable(employeeService.getEmployeeDetails(id));
        System.out.println("EmployeeController.getEmployeeDetails: " + employeeResponse);
        return employeeResponse
                .map(response -> new ResponseEntity<>(response, responseHeaders, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeDetails() {
        System.out.println("EmployeeController.getEmployees: " );
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<EmployeeResponse> employeeResponse = employeeService.getEmployees();
        System.out.println("EmployeeController.getEmployeeDetails: " + employeeResponse);
        return  new ResponseEntity<>(employeeResponse, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody Employee employee) {
        System.out.println("EmployeeController.saveEmployee: " + employee);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(employeeService.saveEmployee(employee), responseHeaders, HttpStatus.OK);
    }
}
