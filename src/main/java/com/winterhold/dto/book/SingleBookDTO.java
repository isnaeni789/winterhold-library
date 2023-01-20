package com.winterhold.dto.book;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SingleBookDTO {
    private String code;
    private String title;
    private String categoryName;
    private Long authorId;
    private Boolean isBorrowed;
    private String summary;
    private LocalDate releaseDate;
    private Integer totalPage;
}
