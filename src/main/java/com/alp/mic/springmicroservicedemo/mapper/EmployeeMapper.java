package com.alp.mic.springmicroservicedemo.mapper;

import com.alp.mic.springmicroservicedemo.model.Employee;
import com.alp.mic.springmicroservicedemo.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {


    @Mapping(target = "bloodgroup", constant = "*")
    EmployeeResponse toResponse(Employee employee);
}
