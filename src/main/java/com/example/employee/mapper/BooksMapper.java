package com.example.employee.mapper;

import com.example.employee.dto.BooksDTO;
import com.example.employee.entity.Books;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BooksMapper
{
    BooksMapper INSTANCE = Mappers.getMapper(BooksMapper.class);

    Books mapBooksDTOToBooks(BooksDTO booksDTO);

    BooksDTO mapBooksToBookDTO(Books books);

    List<Books> mapBooksDToListToBooksList(List<BooksDTO> booksDTOList);

    List<BooksDTO> mapBooksListToBooksDTOList(List<Books> booksList);
}
