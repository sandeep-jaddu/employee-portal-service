package com.example.employee.service;

import com.example.employee.dto.BooksDTO;
import com.example.employee.entity.Books;
import com.example.employee.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksService {
    private static final Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksRepository booksRepository;

    // Convert BooksDTO to Books entity manually
    private Books convertToEntity(BooksDTO booksDTO) {
        Books books = new Books();
        books.setId(booksDTO.getId());
        books.setName(booksDTO.getName());
        books.setAuthor(booksDTO.getAuthor());
        books.setDescription(booksDTO.getDescription());
        books.setNumberOfPages(booksDTO.getNumberOfPages());
        books.setPrice(booksDTO.getPrice());
        books.setBookCreatedBy(booksDTO.getBookCreatedBy());
        books.setDeleted(booksDTO.isDeleted());
        return books;
    }

    // Convert Books entity to BooksDTO manually
    private BooksDTO convertToDTO(Books books) {
        return new BooksDTO(
                books.getId(),
                books.getName(),
                books.getAuthor(),
                books.getDescription(),
                books.getNumberOfPages(),
                books.getPrice(),
                books.getBookCreatedBy(),
                books.isDeleted()
        );
    }

    // Save Book
    public BooksDTO saveBook(BooksDTO booksDTO) {
        logger.info("Received BooksDTO: {}", booksDTO); // Log input
        Books books = convertToEntity(booksDTO);
        Books savedBook = booksRepository.save(books);
        logger.info("Saved Books Entity: {}", savedBook);
        return convertToDTO(savedBook);
    }

    // Get All Books
    public List<BooksDTO> getAllBooks() {
        List<Books> books = booksRepository.findAll();
        return books.stream()
                .map(this::convertToDTO) // Convert each entity to DTO
                .collect(Collectors.toList());
    }

    // Find Book by ID
    public Optional<BooksDTO> findBookById(Integer id) {
        return booksRepository.findById(id).map(this::convertToDTO);
    }

    // Update Book by ID
    public Optional<BooksDTO> updateBookById(Integer id, BooksDTO booksDTO) {
        return booksRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setName(booksDTO.getName());
                    existingBook.setAuthor(booksDTO.getAuthor());
                    existingBook.setDescription(booksDTO.getDescription());
                    existingBook.setNumberOfPages(booksDTO.getNumberOfPages());
                    existingBook.setPrice(booksDTO.getPrice());
                    existingBook.setBookCreatedBy(booksDTO.getBookCreatedBy());
                    existingBook.setDeleted(booksDTO.isDeleted());

                    Books updatedBook = booksRepository.save(existingBook);
                    return convertToDTO(updatedBook);
                });
    }

    // Delete Book by ID
    public boolean deleteBookById(Integer id) {
        if (booksRepository.existsById(id)) {
            booksRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
