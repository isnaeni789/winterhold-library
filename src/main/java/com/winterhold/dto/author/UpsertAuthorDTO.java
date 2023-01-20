package com.winterhold.dto.author;

import com.winterhold.validator.InputDate;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@InputDate(message = "Tanggal kematian tidak mungkin sebelum tanggal lahir",
        previousDateField = "birthDate", subsequentDateField = "deceasedDate")
public class UpsertAuthorDTO {

    private Long id;

    @NotBlank(message = "Title tidak boleh kosong")
    @Size(max = 10, message = "Maksimal 10 karakter")
    private String title;

    @NotBlank(message = "Nama depan tidak boleh kosong")
    @Size(max = 50, message = "Maksimal 50 karakter")
    private String firstName;

    @Size(max = 50, message = "Maksimal 50 karakter")
    private String lastName;

    @NotNull(message = "Tanggal lahir tidak boleh kosong")
    @Past(message = "Author tidak mungkin lahir di masa depan.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Author belum meninggal saat ini.")
    private LocalDate deceasedDate;

    @Size(max = 50, message = "Maksimal 50 karakter")
    private String education;

    @Size(max = 500, message = "Maksimal 500 karakter")
    private String summary;
}
