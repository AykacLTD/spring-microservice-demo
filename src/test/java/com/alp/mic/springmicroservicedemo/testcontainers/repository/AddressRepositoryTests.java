package com.alp.mic.springmicroservicedemo.testcontainers.repository;

import com.alp.mic.springmicroservicedemo.model.Address;
import com.alp.mic.springmicroservicedemo.repository.AddressRepository;
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
public class AddressRepositoryTests {
    @Autowired
    private AddressRepository addressRepository;

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
        addressRepository.deleteAll();
    }

    @Test
    public void testAddressSave() {
        Assertions.assertTrue(!addressRepository.findAll().iterator().hasNext());
        Address address = new Address();
        address.setStreet1("123 Main St");
        address.setZip("60007");
        address.setState("IL");
        address.setEmployeeid(1);
        addressRepository.save(address);
        Assertions.assertTrue(addressRepository.findAll().iterator().hasNext());
        Assertions.assertTrue(addressRepository.findAll().iterator().next().getId() >= 1);
        Assertions.assertEquals("123 Main St", addressRepository.findAll().iterator().next().getStreet1());
        Assertions.assertEquals("60007", addressRepository.findAll().iterator().next().getZip());
        Assertions.assertEquals("IL", addressRepository.findAll().iterator().next().getState());
        Assertions.assertTrue(addressRepository.findAll().iterator().next().getId() >= 1);

    }
}
