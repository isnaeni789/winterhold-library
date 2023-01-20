package com.winterhold.dto.loan;

import lombok.*;

import java.time.LocalDate;

@Data @Builder
public class SingleLoanDTO {
    private Long id;
    private String customerNumber;
    private String bookCode;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String note;
}
