package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDTO {

    @NotBlank(message = "Code Tidak boleh kosong")
    @Size(max = 20, message = "Maksimal 20 karakter")
    private String code;

    @NotBlank(message = "Title tidak boleh kosong")
    @Size(max = 100, message = "Maksimal 100 karakter")
    private String title;

    @NotBlank(message = "Category tidak boleh kosong")
    @Size(max = 100, message = "Maksimal 100 karakter")
    private String categoryName;

    @NotNull(message = "Author Id Tidak boleh kosong")
    private Long authorId;

    private Boolean isBorrowed;

    @Size(max = 500, message = "Maksimal 500 karakter")
    private String summary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Min(value = 0, message = "Minimal 0")
    private Integer totalPage;
}
