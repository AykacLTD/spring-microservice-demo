package com.alp.mic.springmicroservicedemo.repository;

import com.alp.mic.springmicroservicedemo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
    Optional<Address> findAddressByEmployeeId(int employeeId);

}
