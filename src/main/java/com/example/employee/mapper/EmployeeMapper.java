package com.example.employee.mapper;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Entity to DTO
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    // DTO to Entity
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
