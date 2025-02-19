package com.example.employee.controller;


import com.alibaba.excel.EasyExcel;
import com.example.employee.dto.RegisterResponseDTO;
import com.example.employee.dto.UserDTO;
import com.example.employee.entity.User;
import com.example.employee.security.JwtService;
import com.example.employee.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody UserDTO userDTO) {
        return service.saveUser(userDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        else
            return "Login Failed";

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return ResponseEntity.ok(service.getAllUsers());
    }

    // New endpoint to export user data to Excel
    @GetMapping("/exportUsersToExcel")
    public void exportUsersToExcel(HttpServletResponse response) {
        try {
            // Set response headers
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("Registered_Users", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            // Fetch data and write to Excel
            List<UserDTO> users = service.getAllUsers();
            EasyExcel.write(response.getOutputStream(), UserDTO.class).sheet("Registered Users").doWrite(users);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to export users to Excel", e);
        }
    }

}