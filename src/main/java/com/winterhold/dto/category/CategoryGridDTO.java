package com.winterhold.dto.category;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGridDTO {
    private String name;
    private Integer floor;
    private String isle;
    private String bay;
    private Long totalBooks;
}
