package com.alp.mic.springmicroservicedemo.testcontainers.controller;

import com.alp.mic.springmicroservicedemo.repository.EmployeeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:testcontainers.properties")
public class EmployeeControllerTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        employeeRepository.deleteAll();
    }

    @Test
    public void testEmployeeSave() {
        Assertions.assertTrue(!employeeRepository.findAll().iterator().hasNext());

        String employee = "{\"name\":\"ali\",\"email\":\"ali@test.com\",\"bloodgroup\":\"A+\"}";
        String employeeExpected = "{id:1,name:\"ali\",\"email\":\"ali@test.com\",\"bloodgroup\":\"*\"}";

        // save employee
        given()
                .contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/api/v1/employees")
                .then()
                .statusCode(200)
                .body("id", equalTo(1)).and() // check post response body
                .body("name", equalTo("ali")).and()
                .body("email", equalTo("ali@test.com" ));

        // check database if the record is saved
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/employees")
                .then()
                .statusCode(200)
                .body("$", 	hasSize(1)).and()
                .body("[0].id", equalTo(1)).and()
                .body("[0].name", equalTo("ali")).and()
                .body("[0].email", equalTo("ali@test.com" ))
                .body("[0].bloodgroup", equalTo("*"));
        ;

    }
}
