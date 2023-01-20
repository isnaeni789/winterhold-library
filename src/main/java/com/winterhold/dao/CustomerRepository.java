package com.winterhold.dao;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
            SELECT new com.winterhold.dto.customer.CustomerGridDTO(
                cus.membershipNumber, CONCAT(cus.firstName, ' ', cus.lastName),
                cus.membershipExpireDate)
            FROM Customer AS cus
            WHERE cus.membershipNumber LIKE %:number%
                AND CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:fullName%
            """)
    Page<CustomerGridDTO> findAll(@Param("number") String membershipNumber,
                                  @Param("fullName") String fullName,
                                  Pageable pageable);

    @Query("""
			SELECT COUNT(*)
			FROM Customer AS cus
			WHERE cus.membershipNumber = :number""")
    Long count(@Param("number") String membershipNumber);

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
                cus.membershipNumber, CONCAT(cus.firstName, ' ', cus.lastName))
            FROM Customer AS cus
            WHERE cus.membershipExpireDate >= :date
            """)
    List<DropdownDTO> getDropdown(@Param("date") LocalDate date);

    @Query("""
            SELECT COUNT(lo.id)
            FROM Loan AS lo
                JOIN lo.customer AS cus
            WHERE cus.membershipNumber = :number
            """)
    Long countById(@Param("number") String number);
}
