package com.winterhold.rest;

import com.winterhold.dto.category.BookCategoryDTO;
import com.winterhold.dto.category.SingleCategoryDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.utility.ErrorDTO;
import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String name){

        try {
            var grid = service.getCategoryGrid(page, name);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }


    @GetMapping("/{name}")
    public ResponseEntity<Object> get(@PathVariable String name){
        try {
            SingleCategoryDTO oneCategory = service.getOneCategory(name);
            return ResponseEntity.status(200).body(oneCategory);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertCategoryDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleCategoryDTO category = service.insertCategory(dto);
                return ResponseEntity.status(201).body(category);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCategoryDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleCategoryDTO category = service.updateCategory(dto);
                return ResponseEntity.status(201).body(category);
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

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable String name){
        try {
            service.delete(name);
            return ResponseEntity.status(200).body("Berhasil Di Hapus");
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/detail-book/{name}")
    public ResponseEntity<Object> detail(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "") String title,
                                         @RequestParam(defaultValue = "") String author,
                                         @PathVariable String name){
        try {
            Page<BookCategoryDTO> grid = service.getBookCategoryGrid(page, title, author, name);
            return ResponseEntity.status(200).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/detail-book-test/{name}")
    public ResponseEntity<Object> detailTest(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "") String title,
                                         @RequestParam(defaultValue = "") String author,
                                         @RequestParam(required = false) Boolean status,
                                         @PathVariable String name){
        try {
            Page<BookCategoryDTO> grid = service.getBookCategoryGrid(page, title, author, name, status);
            return ResponseEntity.status(200).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }


}
