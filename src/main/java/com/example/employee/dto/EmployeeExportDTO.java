package com.example.employee.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExportDTO {

    @ExcelProperty("Employee ID")
    private Long id;

    @ExcelProperty("Name")
    private String name;

    @ExcelProperty("Age")
    private int age;

    @ExcelProperty("Phone")
    private String phone;

    @ExcelProperty("Address")
    private String address;

    @ExcelProperty("City")
    private String city;
}
