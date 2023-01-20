package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookGridDTO {
    private String code;
    private String bookTitle;
    private String author;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;

    public BookGridDTO(String code,
                       String bookTitle,
                       String author,
                       Boolean isBorrowed,
                       LocalDate releaseDate,
                       Integer totalPage) {
        this.code = code;
        this.bookTitle = bookTitle;
        this.author = author;
        this.isBorrowed = status(isBorrowed);
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }

    private String status(Boolean isBorrowed){
        return isBorrowed ? "Borrowed" : "Available";
    }
}
