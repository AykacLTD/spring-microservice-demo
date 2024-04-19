package com.alp.mic.springmicroservicedemo.mapper;


import com.alp.mic.springmicroservicedemo.model.Address;
import com.alp.mic.springmicroservicedemo.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "street1", target = "street2")
    @Mapping(source = "street2", target = "street1")
    AddressResponse toResponse(Address address);
}
