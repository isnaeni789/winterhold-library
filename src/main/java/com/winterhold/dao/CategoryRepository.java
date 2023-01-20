package com.winterhold.dao;

import com.winterhold.dto.category.BookCategoryDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("""
            SELECT new com.winterhold.dto.category.CategoryGridDTO(
                cat.name, cat.floor, cat.isle, cat.bay, COUNT(bok.code))
            FROM Book AS bok
               RIGHT JOIN bok.category AS cat
            GROUP BY cat.name, cat.floor, cat.isle, cat.bay
            HAVING cat.name LIKE %:name%
            """)
    Page<CategoryGridDTO> findByName(@Param("name") String name, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.category.BookCategoryDTO(
                bok.code, bok.title, CONCAT(aut.title, ' ',
                aut.firstName, ' ', aut.lastName), bok.isBorrowed,
                bok.releaseDate, bok.totalPage)
            FROM Book AS bok
                JOIN bok.author AS aut
                JOIN bok.category AS cat
            WHERE bok.title LIKE %:title%
                AND CONCAT(aut.title, ' ',
                    aut.firstName, ' ', aut.lastName) LIKE %:author%
                AND cat.name = :category
            """)
    Page<BookCategoryDTO> findBookCategory(@Param("title") String title,
                                  @Param("author") String author,
                                  @Param("category") String category,
                                  Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.category.BookCategoryDTO(
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
                AND bok.isBorrowed != :status
            """)
    Page<BookCategoryDTO> findBookCategory(@Param("title") String title,
                                           @Param("author") String author,
                                           @Param("category") String category,
                                           @Param("status") Boolean status,
                                           Pageable pageable);

    @Query("""
            SELECT COUNT(cat.name)
            FROM Category AS cat
            WHERE cat.name = :name
            """)
    Long checkValidName(@Param("name") String name);

    @Query("""
            SELECT COUNT(bok.code)
            FROM Book AS bok
                JOIN bok.category AS cat
            WHERE cat.name = :name
            """)
    Long countById(@Param("name") String name);

}
