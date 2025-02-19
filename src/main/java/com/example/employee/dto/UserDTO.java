package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.excel.annotation.ExcelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @ExcelProperty("ID")
    private int id;

    @ExcelProperty("Username")
    private String username;

    @ExcelProperty("EmailId")
    private String emailId;

    @ExcelProperty("Password")
    private String password;
}
