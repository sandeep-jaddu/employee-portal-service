package com.example.employee.mapper;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    // Entity to DTO
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    // DTO to Entity
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
