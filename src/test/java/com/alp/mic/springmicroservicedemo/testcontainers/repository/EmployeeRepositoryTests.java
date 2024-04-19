package com.alp.mic.springmicroservicedemo.testcontainers.repository;

import com.alp.mic.springmicroservicedemo.model.Employee;
import com.alp.mic.springmicroservicedemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:testcontainers.properties")
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    /*
    * yo can use magic-url in testcontainers.properties file
    * which is jdbc:tc:postgresql:11://bk,
    * in this case, testcontainers will create and
    * set up an ephemeral postgresql container automatically
    * OR
    * you can use @Container and @DynamicPropertySource to set up the ephemeral postgresql container
    */


//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11");
//
//    @DynamicPropertySource
//    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//    };

    @BeforeEach
    public void setup() {
        //clean database
        employeeRepository.deleteAll();
    }


    @Test
    public void testEmployeeSave() {
        Assertions.assertTrue(!employeeRepository.findAll().iterator().hasNext());
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john@test.com");
        employee.setBloodgroup("A+");
        employeeRepository.save(employee);
        Assertions.assertTrue(employeeRepository.findAll().iterator().hasNext());
        Assertions.assertTrue(employeeRepository.findAll().iterator().next().getId() >= 1);
        Assertions.assertEquals("John Doe", employeeRepository.findAll().iterator().next().getName());
        Assertions.assertEquals("john@test.com", employeeRepository.findAll().iterator().next().getEmail());
        Assertions.assertEquals("A+", employeeRepository.findAll().iterator().next().getBloodgroup());
    }
}
