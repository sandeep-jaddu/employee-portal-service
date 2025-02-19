package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksDTO
{
    private int id;
    private String name;
    private String author;
    private String description;
    private int numberOfPages;
    private double price;
    private int bookCreatedBy;
    private boolean isDeleted;
}
