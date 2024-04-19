package com.alp.mic.springmicroservicedemo.mockTests;

import com.alp.mic.springmicroservicedemo.model.Employee;
import com.alp.mic.springmicroservicedemo.response.EmployeeResponse;
import com.alp.mic.springmicroservicedemo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    @Test
    public void testGetEmployees() throws Exception {
        int employeeId = 1;

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(1);
        employeeResponse.setName("user");
        employeeResponse.setEmail("user@test.com");

        when(employeeService.getEmployeeDetails(employeeId)).thenReturn(employeeResponse);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        RequestBuilder request = get("/api/v1/employees/1")
                .headers(requestHeaders);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"user\",\"email\":\"user@test.com\"}"));
    }

    @Test
    public void getAddressByEmployeeIdReturnsNotFoundWhenEmployeeDoesNotExist() throws Exception {
        int employeeId = 2;
        when(employeeService.getEmployeeDetails(employeeId)).thenReturn(null);

        mockMvc.perform(get("/employee/" + employeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewEmployee() throws Exception {

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(1);
        employeeResponse.setName("user1");
        employeeResponse.setEmail("user1@test.com");
        employeeResponse.setBloodgroup("*");

        String createReqContent
                = "{\"name\":\"user1\",\"email\":\"user1@test.com\",\"bloodgroup\":\"A+\"}";
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employeeResponse);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        MockHttpServletRequestBuilder request = post("/api/v1/employees")
                .content(createReqContent)
                .headers(requestHeaders);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"user1\",\"email\":\"user1@test.com\", \"bloodgroup\":\"*\"}"));
    }



}