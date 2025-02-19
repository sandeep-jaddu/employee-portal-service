package com.example.employee.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
public class EmployeeDetailsDTO {

    @ExcelProperty("Employee ID") // Column Name in Excel
    private Long id;

    @ExcelProperty("Name")
    private String name;

    @ExcelProperty("Age")
    private int age;

    @ExcelProperty("Phone")
    private String phone;

    // Nested lists like "addresses" cannot be written directly in EasyExcel
    private List<AddressDTO> addresses;

    @Setter
    @Getter
    @Data
    @AllArgsConstructor
    public static class AddressDTO {
        @ExcelProperty("Address")
        private String address;

        @ExcelProperty("City")
        private String city;
    }
}
