package com.example.employee.mapper;

import com.example.employee.dto.AddressDTO;
import com.example.employee.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper
{
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    // Entity to DTO
    AddressDTO addressToAddressDTO(Address address);

    // DTO to Entity
    Address addressDTOToAddress(AddressDTO addressDTO);
}
