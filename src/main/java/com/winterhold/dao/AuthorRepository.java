package com.winterhold.dao;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.BookAuthorDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
            SELECT new com.winterhold.dto.author.AuthorGridDTO(
                aut.id, CONCAT(aut.title, ' ',aut.firstName, ' ', aut.lastName),
                aut.birthDate, aut.deceasedDate, aut.education)
            FROM Author AS aut
            WHERE CONCAT(aut.firstName, ' ', aut.lastName) LIKE %:fullName%
            """)
    Page<AuthorGridDTO> findByName(@Param("fullName") String fullName, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.author.BookAuthorDTO(
                bok.title, cat.name, bok.isBorrowed,
                bok.releaseDate, bok.totalPage)
            FROM Book AS bok
                JOIN bok.author AS aut
                JOIN bok.category AS cat
            WHERE aut.id = :id
            """)
    Page<BookAuthorDTO> findBookAuthor(@Param("id") Long id,Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
                aut.id, CONCAT(aut.firstName, ' ', aut.lastName))
            FROM Author as aut
            """)
    List<DropdownDTO> getDropdown();

    @Query("""
            SELECT COUNT(bok.code)
            FROM Book AS bok
                JOIN bok.author AS aut
            WHERE aut.id = :id
            """)
    Long countById(@Param("id") Long id);



}
