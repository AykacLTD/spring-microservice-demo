package com.alp.mic.springmicroservicedemo.controller;

import com.alp.mic.springmicroservicedemo.model.Address;
import com.alp.mic.springmicroservicedemo.response.AddressResponse;
import com.alp.mic.springmicroservicedemo.response.EmployeeResponse;
import com.alp.mic.springmicroservicedemo.service.AddressService;
import com.alp.mic.springmicroservicedemo.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;
    private final EmployeeService employeeService;

    public AddressController(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int id) {
        System.out.println("AddressController.getAddressDetails: " + id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<AddressResponse> addressResponse = Optional.ofNullable(addressService.getAddressByEmployeeId(id));
        System.out.println("AddressController.getAddressDetails: " + addressResponse);
        return addressResponse
                .map(response -> new ResponseEntity<>(response, responseHeaders, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<AddressResponse> addressResponse = addressService.getAllAddresses();
        System.out.println("AddressController.getAddressDetails: " + addressResponse);
        return (new ResponseEntity<>(addressResponse, responseHeaders, HttpStatus.OK));
    }

    @PostMapping("/employee/{id}")
    public ResponseEntity<AddressResponse> saveAddress(@RequestBody Address address, @PathVariable("id") int id) {
        System.out.println("AddressController.saveAddress: " + address);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (employeeService.getEmployeeDetails(id) == null) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
        // actually no need to call this method, just for the sake of demo, we can use id directly, no need for empId
        address.setEmployeeid(employeeService.getEmployeeDetails(id).getId());

        return new ResponseEntity<>(addressService.saveAddress(address), responseHeaders, HttpStatus.OK);
    }
}
