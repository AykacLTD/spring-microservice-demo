package com.alp.mic.springmicroservicedemo.service;

import com.alp.mic.springmicroservicedemo.mapper.AddressMapper;
import com.alp.mic.springmicroservicedemo.model.Address;
import com.alp.mic.springmicroservicedemo.repository.AddressRepository;
import com.alp.mic.springmicroservicedemo.response.AddressResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressResponse getAddressByEmployeeId(Integer id) {
        System.out.println("AddressbyId: " + id);

        return addressRepository.findAddressByEmployeeId(id)
                .map(addressMapper::toResponse)
                .orElse(null);
    }

    public List<AddressResponse> getAllAddresses() {

        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AddressResponse saveAddress(Address address) {
        System.out.println("AddressController.saveAddress: " + address);
        return addressMapper.toResponse(addressRepository.save(address));
    }
}
