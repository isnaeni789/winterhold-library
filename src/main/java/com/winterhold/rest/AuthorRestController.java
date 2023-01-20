package com.winterhold.rest;

import com.winterhold.dto.author.BookAuthorDTO;
import com.winterhold.dto.author.SingleAuthorDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.utility.ErrorDTO;
import com.winterhold.service.AuthorService;
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
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    private AuthorService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String name){

        try {
            var grid = service.getAuthorGrid(page, name);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            SingleAuthorDTO oneAuthor = service.getOneAuthor(id);
            return ResponseEntity.status(200).body(oneAuthor);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid UpsertAuthorDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleAuthorDTO author = service.saveAuthor(dto);
                return ResponseEntity.status(201).body(author);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertAuthorDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleAuthorDTO author = service.saveAuthor(dto);
                return ResponseEntity.status(201).body(author);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.status(200).body("Berhasil Di Hapus");
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/detail-book/{id}")
    public ResponseEntity<Object> detail(@RequestParam(defaultValue = "1") Integer page,
                                         @PathVariable Long id){
        try {
            Page<BookAuthorDTO> grid = service.getBookAuthorGrid(page, id);
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
