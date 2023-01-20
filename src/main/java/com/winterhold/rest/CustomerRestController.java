package com.winterhold.rest;

import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.dto.utility.ErrorDTO;
import com.winterhold.service.CustomerService;
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
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String membership,
            @RequestParam(defaultValue = "") String name){

        try {
            var grid = service.getCustomerGrid(page, membership, name);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/{membership}")
    public ResponseEntity<Object> get(@PathVariable String membership){
        try {
            SingleCustomerDTO oneCustomer = service.getOneCustomer(membership);
            return ResponseEntity.status(200).body(oneCustomer);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertCustomerDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleCustomerDTO customer = service.insertCustomer(dto);
                return ResponseEntity.status(201).body(customer);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCustomerDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleCustomerDTO customer = service.updateCustomer(dto);
                return ResponseEntity.status(201).body(customer);
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

    @DeleteMapping("/{membership}")
    public ResponseEntity<Object> delete(@PathVariable String membership){
        try {
            service.delete(membership);
            return ResponseEntity.status(200).body("Berhasil Di Hapus");
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/extend/{membership}")
    public ResponseEntity<Object> extend(@PathVariable String membership){
        try {
            SingleCustomerDTO extend = service.extend(membership);
            return ResponseEntity.status(200).body(extend);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}
