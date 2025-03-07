package com.example.employee.controller;

import com.alibaba.excel.EasyExcel;
import com.example.employee.dto.EmployeeDetailsDTO;
import com.example.employee.dto.EmployeeExportDTO;
import com.example.employee.entity.Address;
import com.example.employee.repository.AddressRepository;
import com.example.employee.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employee-address")
public class EmployeeAddressController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/getEmployeeDetailsWithAddressById/{employeeId}")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(@PathVariable Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    List<Address> addresses = addressRepository.findByEmployeeId(employeeId);
                    List<EmployeeDetailsDTO.AddressDTO> addressDTOs = addresses.stream()
                            .map(address -> new EmployeeDetailsDTO.AddressDTO(address.getAddress(), address.getCity()))
                            .toList();
                    EmployeeDetailsDTO details = new EmployeeDetailsDTO(employee.getId(), employee.getName(), employee.getAge(), employee.getPhone(), addressDTOs);
                    return ResponseEntity.ok(details);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exportEmployeesToExcel")
    public void exportUsersToExcel(HttpServletResponse response) {
        try {
            // Set response headers
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("Employee_Info", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            // Fetch and flatten employee data
            List<EmployeeExportDTO> employeeExportList = getFlattenedEmployeeDetails();

            // Write data to Excel
            EasyExcel.write(response.getOutputStream(), EmployeeExportDTO.class)
                    .sheet("Employees Info")
                    .doWrite(employeeExportList);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to export employees to Excel", e);
        }
    }

    /**
     * Fetches all employees with their addresses and returns a flattened list of EmployeeExportDTO.
     */
    private List<EmployeeExportDTO> getFlattenedEmployeeDetails() {
        List<EmployeeExportDTO> exportList = new ArrayList<>();

        employeeRepository.findAll().forEach(employee ->  {
            List<Address> addresses = addressRepository.findByEmployeeId(employee.getId());

            if (addresses.isEmpty()) {
                // If the employee has no addresses, write a row with an empty address
                exportList.add(new EmployeeExportDTO(
                        employee.getId(), employee.getName(), employee.getAge(), employee.getPhone(),
                        "", "" // Empty address and city
                ));
            } else {
                // Otherwise, write each address as a separate row
                addresses.forEach(address -> {
                    exportList.add(new EmployeeExportDTO(
                            employee.getId(), employee.getName(), employee.getAge(), employee.getPhone(),
                            address.getAddress(), address.getCity()
                    ));
                });
            }
        });

        return exportList;
    }
}
