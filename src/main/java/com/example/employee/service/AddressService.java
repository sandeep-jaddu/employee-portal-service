package com.example.employee.service;

import com.example.employee.entity.Address;
import com.example.employee.dto.AddressDTO;
import com.example.employee.mapper.AddressMapper;
import com.example.employee.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

//    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
//        this.addressRepository = addressRepository;
//        this.addressMapper = addressMapper;
//    }

    public AddressDTO saveAddress(AddressDTO addressDTO) {
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.addressToAddressDTO(address);
    }

    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(addressMapper::addressToAddressDTO)
                .toList();
    }

    public Optional<AddressDTO> getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::addressToAddressDTO);
    }

    public Optional<AddressDTO> updateAddress(Long id, AddressDTO updatedAddressDTO) {
        return addressRepository.findById(id).map(existingAddress -> {
            existingAddress.setAddress(updatedAddressDTO.getAddress());
            existingAddress.setCity(updatedAddressDTO.getCity());
            existingAddress.setEmployeeId(updatedAddressDTO.getEmployeeId()); // Make sure employeeId is being set here
            Address updatedAddress = addressRepository.save(existingAddress);
            return addressMapper.addressToAddressDTO(updatedAddress);
        });
    }

    public boolean deleteAddressById(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            logger.info("✅ Successfully deleted Address with ID: {}", id);
            return true;
        }
        logger.warn("⚠️ Attempted to delete non-existent Address with ID: {}", id);
        return false;
    }
}
