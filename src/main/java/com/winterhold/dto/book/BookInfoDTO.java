package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@AllArgsConstructor
public class BookInfoDTO {
    private String code;
    private String title;
    private String categoryName;
    private String author;
    private Integer floor;
    private String isle;
    private String bay;
}
