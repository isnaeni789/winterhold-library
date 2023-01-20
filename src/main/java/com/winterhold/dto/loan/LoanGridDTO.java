package com.winterhold.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanGridDTO {
    private Long id;
    private String bookTitle;
    private String customerName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
