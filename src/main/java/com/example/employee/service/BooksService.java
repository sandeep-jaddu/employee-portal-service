package com.example.employee.service;

import com.example.employee.dto.BooksDTO;
import com.example.employee.entity.Books;
import com.example.employee.mapper.BooksMapper;
import com.example.employee.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService
{
    private static final Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksRepository booksRepository;


    public BooksDTO saveBook(BooksDTO booksDTO) {
        logger.info("Received BooksDTO: {}", booksDTO); // Log input
        Books books = BooksMapper.INSTANCE.mapBooksDTOToBooks(booksDTO);
        logger.info("Mapped Books Entity: {}", books); // Log mapped entity

        Books savedBook = booksRepository.save(books);
        logger.info("Saved Books Entity: {}", savedBook); // Log DB entry

        BooksDTO responseDto = BooksMapper.INSTANCE.mapBooksToBookDTO(savedBook);
        logger.info("Response BooksDTO: {}", responseDto); // Log response
        return responseDto;
    }


    public List<BooksDTO> getAllBooks()
    {
//        return booksRepository.findAll().stream().map(
//                BooksMapper.INSTANCE::mapBooksToBookDTO
//        ).toList();
       List<Books> books = booksRepository.findAll();
       return BooksMapper.INSTANCE.mapBooksListToBooksDTOList(books);
    }


    public Optional<BooksDTO> findBookById(Integer id)
    {
        return booksRepository.findById(id).map(
                BooksMapper.INSTANCE::mapBooksToBookDTO
        );
    }


    public Optional<BooksDTO> updateBookById(Integer id, BooksDTO booksDTO)
    {
        return booksRepository.findById(id)
                .map(
                        existingBook -> {
                            existingBook.setName(booksDTO.getName());
                            existingBook.setAuthor(booksDTO.getAuthor());
                            existingBook.setDescription(booksDTO.getDescription());
                            existingBook.setNumberOfPages(booksDTO.getNumberOfPages());
                            existingBook.setPrice(booksDTO.getPrice());
                            existingBook.setBookCreatedBy(booksDTO.getBookCreatedBy());
                            existingBook.setDeleted(booksDTO.isDeleted());
                            Books savingUpdatedBook = booksRepository.save(existingBook);
                            return BooksMapper.INSTANCE.mapBooksToBookDTO(savingUpdatedBook);
                        }
                );
    }


    public boolean deleteBookById(Integer id)
    {
        if(booksRepository.existsById(id))
        {
            booksRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
