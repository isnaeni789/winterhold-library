package com.winterhold.service;

import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.book.BookInfoDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.SingleLoanDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Customer;
import com.winterhold.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Page<LoanGridDTO> getLoanGrid(Integer pageNumber, String title, String name){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        return loanRepository.findAll(title, name, pageable);
    }

    public SingleLoanDTO getOneLoan(Long id){
        Loan loan = loanRepository.findById(id).get();
        return SingleLoanDTO.builder()
                .id(loan.getId())
                .customerNumber(loan.getCustomerNumber())
                .bookCode(loan.getBookCode())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .note(loan.getNote())
                .build();
    }

    public SingleLoanDTO insert(UpsertLoanDTO dto){
        Loan loan = new Loan(
                dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dto.getLoanDate().plusDays(5),
                dto.getReturnDate(),
                dto.getNote());
        Book book = bookRepository.findById(dto.getBookCode()).get();
        book.setIsBorrowed(true);
        bookRepository.save(book);
        loanRepository.save(loan);
        return SingleLoanDTO.builder()
                .id(loan.getId())
                .customerNumber(loan.getCustomerNumber())
                .bookCode(loan.getBookCode())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .note(loan.getNote())
                .build();
    }

    public SingleLoanDTO update(UpsertLoanDTO dto){
        Loan loan = new Loan(
                dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dto.getDueDate(),
                dto.getReturnDate(),
                dto.getNote());
        loanRepository.save(loan);
        return SingleLoanDTO.builder()
                .id(loan.getId())
                .customerNumber(loan.getCustomerNumber())
                .bookCode(loan.getBookCode())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .note(loan.getNote())
                .build();
    }

    public UpsertLoanDTO getUpdate(Long id){
        var entity = loanRepository.findById(id).get();
        return new UpsertLoanDTO(
                entity.getId(),
                entity.getCustomerNumber(),
                entity.getBookCode(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                entity.getNote());
    }

    public void returnBook(Long id){
        Loan loan = loanRepository.findById(id).get();
        if (loan.getReturnDate() == null){
            loan.setReturnDate(LocalDate.now());
            Book book = bookRepository.findById(loan.getBookCode()).get();
            book.setIsBorrowed(false);
            bookRepository.save(book);
        }
        loanRepository.save(loan);
    }

    public BookInfoDTO getOneBook(Long id){
        Loan loan = loanRepository.findById(id).get();
        BookInfoDTO bookInfo = bookRepository.getBookInfo(loan.getBookCode());
        return bookInfo;
    }

    public SingleCustomerDTO getOneCustomer(Long id){
        Loan loan = loanRepository.findById(id).get();
        Customer dto = customerRepository.findById(loan.getCustomerNumber()).get();
        return SingleCustomerDTO.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .membershipExpireDate(dto.getMembershipExpireDate())
                .build();
    }

    public List<DropdownDTO> getCustomerDropdown(){
        return customerRepository.getDropdown(LocalDate.now());
    }

    public List<DropdownDTO> getBookDropdown(){
        return bookRepository.getDropdown();
    }

    public List<DropdownDTO> getAllBookDropdown(){
        return bookRepository.getAllDropdown();
    }
}
