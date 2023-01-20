package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO {
    private String bookTitle;
    private String category;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;

    public BookAuthorDTO(String bookTitle,
                         String category,
                         Boolean isBorrowed,
                         LocalDate releaseDate,
                         Integer totalPage) {
        this.bookTitle = bookTitle;
        this.category = category;
        this.isBorrowed = status(isBorrowed);
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }

    private String status(Boolean isBorrowed){
        return isBorrowed ? "Borrowed" : "Available";
    }
}
