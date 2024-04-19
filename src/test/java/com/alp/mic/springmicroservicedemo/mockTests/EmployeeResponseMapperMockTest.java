package com.alp.mic.springmicroservicedemo.mockTests;

import com.alp.mic.springmicroservicedemo.mapper.EmployeeMapper;
import com.alp.mic.springmicroservicedemo.model.Employee;
import com.alp.mic.springmicroservicedemo.response.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmployeeResponseMapperMockTest {

    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    public void toResponseReturnsCorrectResponseWhenEmployeeIsNotNull() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Test");
        employee.setEmail("Test@test.com");

        EmployeeResponse response = employeeMapper.toResponse(employee);

        assertEquals(employee.getId(), response.getId());
        assertEquals(employee.getName(), response.getName());
        assertEquals(employee.getEmail(), response.getEmail());
    }

    @Test
    public void toResponseReturnsNullWhenEmployeeIsNull() {
        Employee employee = null;
        EmployeeResponse response = employeeMapper.toResponse(employee);
        assertNull(response);
    }
}