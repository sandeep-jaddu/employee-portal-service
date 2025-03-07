package com.example.employee.service;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.entity.Employee;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);


    @Autowired
    private EmployeeRepository employeeRepository;


//    // Constructor injection for EmployeeRepository and EmployeeMapper
//    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
//        this.employeeRepository = employeeRepository;
//        this.employeeMapper = employeeMapper;
//    }

    // Method to save Employee
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(EmployeeMapper.INSTANCE.employeeDTOToEmployee(employeeDTO));
        return EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employee);
    }

    // Method to get all Employees
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Employees fetched from DB: {}", employees); // Log the raw employee entities
        return employees.stream()
                .map(EmployeeMapper.INSTANCE::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }


    // Method to get Employee by ID
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper.INSTANCE::employeeToEmployeeDTO);
    }

    // Method to update Employee
    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
        return employeeRepository.findById(id).map(existingEmployee -> {
            existingEmployee.setName(updatedEmployeeDTO.getName());
            existingEmployee.setAge(updatedEmployeeDTO.getAge());
            existingEmployee.setPhone(updatedEmployeeDTO.getPhone());
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return EmployeeMapper.INSTANCE.employeeToEmployeeDTO(updatedEmployee);
        });
    }

    // Method to delete Employee by ID
    public boolean deleteEmployeeById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            logger.info("✅ Successfully deleted Employee with ID: {}", id);
            return true;
        }
        logger.warn("⚠️ Attempted to delete non-existent Employee with ID: {}", id);
        return false;
    }
}
