package com.alp.mic.springmicroservicedemo.testcontainers.controller;

import com.alp.mic.springmicroservicedemo.repository.AddressRepository;
import com.alp.mic.springmicroservicedemo.repository.EmployeeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:testcontainers.properties")
public class AddressControllerTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private Integer port;
    @Autowired
    private AddressRepository addressRepository;

    private int employeeId;

    private void createEmployee() {
        String employeeJson = "{\"name\":\"ali\",\"email\":\"ali@test.com\",\"bloodgroup\":\"A+\"}";
        String url = "http://localhost:" + port + "/api/v1/employees";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(employeeJson, headers);

        String response = restTemplate.postForObject(url, request, String.class);
        employeeId = Integer.parseInt(response.split(":")[1].split(",")[0]);

        System.out.println("Response: " + response);
        System.out.println("Employee ID: " + employeeId);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        //clean database
        addressRepository.deleteAll();
        //create an employee
        createEmployee();
    }


    @Test
    public void testEmployeeSave() {
        Assertions.assertTrue(!addressRepository.findAll().iterator().hasNext());

        String employee = "{\"name\":\"ali\",\"email\":\"ali@test.com\",\"bloodgroup\":\"A+\"}";

        String address = "{\"street1\":\"March Road\",\"street2\":\"Karen Court\",\"state\":\"California\",\"zip\":\"95014\"}";
        String addressExpected = "{\"street1\":\"March Road\",\"street2\":\"Karen Court\",\"state\":\"California\",\"zip\":\"95014\"}";

;
        // save address for the employee
        given()
                .contentType(ContentType.JSON)
                .body(address)
                .when()
                .post("/api/v1/addresses/employee/"+employeeId) //assuming new employee's id is 1
                .then()
                .statusCode(200)
                .body("id", equalTo(1)).and() // check post response body
                .body("street1", equalTo("Karen Court")).and() // custom bean mapping swaps street1 and street2
                .body("street2", equalTo("March Road"));

        // check database if the record is saved
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/addresses")
                .then()
                .statusCode(200)
                .body("$", hasSize(1)).and()
                .body("[0].street1", equalTo("Karen Court")).and()
                .body("[0].street2", equalTo("March Road")).and()
                .body("[0].state", equalTo("California"))
                .body("[0].zip", equalTo("95014"));


    }
}
