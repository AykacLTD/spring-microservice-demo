package com.alp.mic.springmicroservicedemo.repository;

import com.alp.mic.springmicroservicedemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
