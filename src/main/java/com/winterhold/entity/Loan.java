package com.winterhold.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "Loan")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CustomerNumber", updatable = false)
    private String customerNumber;

    @ManyToOne
    @JoinColumn(name = "CustomerNumber", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "BookCode", updatable = false)
    private String bookCode;

    @ManyToOne
    @JoinColumn(name = "BookCode", insertable = false, updatable = false)
    private Book book;

    @Column(name = "LoanDate", updatable = false)
    private LocalDate loanDate;

    @Column(name = "DueDate", updatable = false)
    private LocalDate dueDate;

    @Column(name = "ReturnDate")
    private LocalDate returnDate;

    @Column(name = "Note")
    private String note;

    public Loan(Long id,
                String customerNumber,
                String bookCode,
                LocalDate loanDate,
                LocalDate dueDate,
                LocalDate returnDate,
                String note) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.bookCode = bookCode;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.note = note;
    }
}
