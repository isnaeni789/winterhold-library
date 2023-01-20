package com.winterhold.dao;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.book.BookInfoDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(
                bok.code, bok.title, CONCAT(aut.title, ' ',
                aut.firstName, ' ', aut.lastName), bok.isBorrowed,
                bok.releaseDate, bok.totalPage)
            FROM Book AS bok
                JOIN bok.author AS aut
                JOIN bok.category AS cat
            WHERE bok.title LIKE %:title%
                AND CONCAT(aut.title, ' ',
                aut.firstName, ' ', aut.lastName) LIKE %:author%
                AND cat.name LIKE %:category%
            """)
    Page<BookGridDTO> findAll(@Param("title") String title,
                              @Param("author") String author,
                              @Param("category") String category,
                              Pageable pageable);

    @Query("""
            SELECT COUNT(*)
            FROM Book AS bok
            WHERE bok.code = :code
            """)
    Long countByCode(@Param("code") String code);

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
                bok.code, bok.title)
            FROM Book AS bok
            WHERE bok.isBorrowed = FALSE
            """)
    List<DropdownDTO> getDropdown();

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
                bok.code, bok.title)
            FROM Book AS bok
            """)
    List<DropdownDTO> getAllDropdown();

    @Query("""
            SELECT new com.winterhold.dto.book.BookInfoDTO(
                bok.code, bok.title, bok.categoryName,
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                cat.floor, cat.isle, cat.bay)
            FROM Book AS bok
                JOIN bok.author AS aut
                JOIN bok.category AS cat
            WHERE bok.code = :code
            """)
    BookInfoDTO getBookInfo(@Param("code") String code);

    @Query("""
            SELECT COUNT(lo.id)
            FROM Loan AS lo
                JOIN lo.book AS bok
            WHERE bok.code = :code
            """)
    Long countTotalDependencies(@Param("code") String code);


}
