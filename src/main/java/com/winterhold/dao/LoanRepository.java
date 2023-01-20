package com.winterhold.dao;

import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
            SELECT new com.winterhold.dto.loan.LoanGridDTO(
                lo.id, bok.title, CONCAT(cus.firstName, ' ', cus.lastName),
                lo.loanDate, lo.dueDate, lo.returnDate)
            FROM Loan AS lo
                JOIN lo.book as bok
                JOIN lo.customer as cus
            WHERE bok.title LIKE %:title%
                AND CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:fullName%
            """)
    Page<LoanGridDTO> findAll(@Param("title") String title,
                              @Param("fullName") String fullName,
                              Pageable pageable);
}
