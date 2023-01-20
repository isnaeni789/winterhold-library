package com.winterhold.dto.category;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SingleCategoryDTO {
    private String name;
    private Integer floor;
    private String isle;
    private String bay;
}
