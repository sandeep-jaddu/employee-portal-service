package com.example.employee.controller;

import com.example.employee.dto.BooksDTO;
import com.example.employee.service.BooksService;
import com.example.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/books")
@RestController
public class BooksController
{

    private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    private BooksService booksService;

    @PostMapping("/addBook")
    public ResponseEntity<BooksDTO> saveBooks(@RequestBody BooksDTO booksDTO)
    {
        BooksDTO savingBook = booksService.saveBook(booksDTO);
        logger.info("Books fetched from Service: {}", savingBook);
        return ResponseEntity.ok(savingBook);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BooksDTO>> getAllBooks()
    {
        return ResponseEntity.ok(booksService.getAllBooks());
    }

    @GetMapping("/findBookById/{id}")
    public ResponseEntity<BooksDTO> getBookById(@PathVariable Integer id)
    {
        return booksService.findBookById(id).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build()
        );
    }

    @PutMapping("/updateBookById/{id}")
    public ResponseEntity<BooksDTO> updateBookById(@PathVariable Integer id, @RequestBody BooksDTO booksDTO)
    {
        return booksService.updateBookById(id,booksDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<BooksDTO> deleteBookById(@PathVariable Integer id)
    {
        return booksService.deleteBookById(id)?
                ResponseEntity.noContent().build():
                ResponseEntity.notFound().build();
    }

}
