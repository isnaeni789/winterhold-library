package com.winterhold.rest;

import com.winterhold.dto.loan.SingleLoanDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.dto.utility.ErrorDTO;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/loan")
public class LoanRestController {

    @Autowired
    private LoanService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String name){

        try {
            var grid = service.getLoanGrid(page, title, name);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            SingleLoanDTO loan = service.getOneLoan(id);
            return ResponseEntity.status(HttpStatus.OK).body(loan);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertLoanDTO dto,
                                       BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleLoanDTO loanDTO = service.insert(dto);
                return ResponseEntity.status(201).body(loanDTO);
            }
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return ResponseEntity.status(422).body(allErrors);
        } catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertLoanDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleLoanDTO loanDTO = service.update(dto);
                return ResponseEntity.status(201).body(loanDTO);
            }
            List<ObjectError> allErrors = bindingResult.getAllErrors();
//            List<ValidationDTO> errors = MapperHelper.getErrors(allErrors);
            return ResponseEntity.status(422).body(allErrors);
        } catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/return/{id}")
    public ResponseEntity<Object> returnBook(@PathVariable Long id){
        try {
            service.returnBook(id);
            return ResponseEntity.status(200).body("Berhasil Di Kembalikan");
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/customer-dropdown")
    public ResponseEntity<Object> customerDropdown(){
        try {
            List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
            return ResponseEntity.status(200).body(customerDropdown);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
    @GetMapping("/book-dropdown")
    public ResponseEntity<Object> bookDropdown(){
        try {
            List<DropdownDTO> bookDropdown = service.getBookDropdown();
            return ResponseEntity.status(200).body(bookDropdown);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}
