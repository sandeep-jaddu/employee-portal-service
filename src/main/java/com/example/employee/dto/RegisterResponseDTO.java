package com.example.employee.dto;

import com.example.employee.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO
{
    private UserDTO user;
    private String message;
    private boolean responseStatus;
}
