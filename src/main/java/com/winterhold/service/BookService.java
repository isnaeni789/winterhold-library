package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.SingleBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Page<CategoryGridDTO> getCategoryGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return categoryRepository.findByName(searchName, pageable);
    }

    public Page<BookGridDTO> getBookGrid(Integer pageNumber, String title, String author, String category){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return bookRepository.findAll(title, author, category, pageable);
    }

    public SingleBookDTO getOneBook(String code){
        Book book = bookRepository.findById(code).get();
        return SingleBookDTO.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .categoryName(book.getCategoryName())
                .authorId(book.getAuthorId())
                .isBorrowed(book.getIsBorrowed())
                .releaseDate(book.getReleaseDate())
                .summary(book.getSummary())
                .totalPage(book.getTotalPage())
                .build();
    }

    public SingleBookDTO insertBook(InsertBookDTO dto){
        Book book = new Book(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage());
        bookRepository.save(book);
        return SingleBookDTO.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .categoryName(book.getCategoryName())
                .authorId(book.getAuthorId())
                .isBorrowed(book.getIsBorrowed())
                .releaseDate(book.getReleaseDate())
                .summary(book.getSummary())
                .totalPage(book.getTotalPage())
                .build();
    }

    public SingleBookDTO updateBook(UpdateBookDTO dto){
        Book book = new Book(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorrowed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage());
        bookRepository.save(book);
        return SingleBookDTO.builder()
                .code(book.getCode())
                .title(book.getTitle())
                .categoryName(book.getCategoryName())
                .authorId(book.getAuthorId())
                .isBorrowed(book.getIsBorrowed())
                .releaseDate(book.getReleaseDate())
                .summary(book.getSummary())
                .totalPage(book.getTotalPage())
                .build();
    }

    public UpdateBookDTO getUpdate(String code){
        var entity = bookRepository.findById(code).get();
        return new UpdateBookDTO(
                entity.getCode(),
                entity.getTitle(),
                entity.getCategoryName(),
                entity.getAuthorId(),
                entity.getIsBorrowed(),
                entity.getSummary(),
                entity.getReleaseDate(),
                entity.getTotalPage());
    }

    public List<DropdownDTO> getAuthorDropdown(){
        return authorRepository.getDropdown();
    }

    public void delete(String code){
        bookRepository.deleteById(code);
    }

    public Boolean isValidBookCode(String code){
        var total = bookRepository.countByCode(code);
        return total < 1;
    }

    public Long getTotalDependencies(String code){
        return bookRepository.countTotalDependencies(code);
    }

}
