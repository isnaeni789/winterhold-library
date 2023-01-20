package com.winterhold.rest;

import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.SingleBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.dto.utility.ErrorDTO;
import com.winterhold.service.BookService;
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
@RequestMapping("/api/book")
public class BookRestController {

    @Autowired
    private BookService service;

    @GetMapping("/category")
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String name){

        try {
            var grid = service.getCategoryGrid(page, name);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> get(@PathVariable String code){
        try {
            SingleBookDTO oneBook = service.getOneBook(code);
            return ResponseEntity.status(200).body(oneBook);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertBookDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleBookDTO bookDTO = service.insertBook(dto);
                return ResponseEntity.status(201).body(bookDTO);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateBookDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleBookDTO bookDTO = service.updateBook(dto);
                return ResponseEntity.status(201).body(bookDTO);
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

    @DeleteMapping("/{code}")
    public ResponseEntity<Object> delete(@PathVariable String code){
        try {
            service.delete(code);
            return ResponseEntity.status(200).body("Berhasil Di Hapus");
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/author-dropdown")
    public ResponseEntity<Object> getAuthor(){
        try {
            List<DropdownDTO> authorDropdown = service.getAuthorDropdown();
            return ResponseEntity.status(200).body(authorDropdown);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}
