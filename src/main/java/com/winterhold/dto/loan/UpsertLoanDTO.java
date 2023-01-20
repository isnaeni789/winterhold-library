package com.winterhold.dto.loan;

import com.winterhold.validator.InputDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@InputDate(message = "Due date tidak mungkin sebelum loan date",
        previousDateField = "loanDate", subsequentDateField = "dueDate")
public class UpsertLoanDTO {

    private Long id;

    @NotBlank(message = "Customer Number Tidak Boleh Kosong")
    private String customerNumber;

    @NotBlank(message = "Book code tidak boleh kosong")
    private String bookCode;

    @NotNull(message = "Tanggal peminjaman tidak boleh kosong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private String note;
}
