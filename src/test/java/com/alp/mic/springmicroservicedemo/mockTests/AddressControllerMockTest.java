package com.alp.mic.springmicroservicedemo.mockTests;

import com.alp.mic.springmicroservicedemo.response.AddressResponse;
import com.alp.mic.springmicroservicedemo.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    private AddressResponse addressResponse;

    @BeforeEach
    public void setup(){
        addressResponse = new AddressResponse();
        addressResponse.setId(1);
        addressResponse.setStreet1("Test Street");
        addressResponse.setStreet2("Apple lane");
        addressResponse.setState("Test City");
        addressResponse.setZip("12345");

    }

    @Test
    public void getAddressByEmployeeIdReturnsAddressWhenEmployeeExists() throws Exception {
        when(addressService.getAddressByEmployeeId(1)).thenReturn(addressResponse);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        RequestBuilder request = get("/api/v1/addresses/employee/1")
                .headers(requestHeaders);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1, \"street1\":\"Test Street\", \"street2\": \"Apple lane\", \"state\":\"Test City\", \"zip\":\"12345\"}"));
    }

    @Test
    public void getAddressByEmployeeIdReturnsNotFoundWhenEmployeeDoesNotExist() throws Exception {
        int employeeId = 2;
        when(addressService.getAddressByEmployeeId(employeeId)).thenReturn(null);

        mockMvc.perform(get("/address/employee/" + employeeId))
                .andExpect(status().isNotFound());
    }
}