package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.BookAuthorDTO;
import com.winterhold.dto.author.SingleAuthorDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Page<AuthorGridDTO> getAuthorGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return authorRepository.findByName(searchName, pageable);
    }

    public SingleAuthorDTO getOneAuthor(Long id){
        Author author = authorRepository.findById(id).get();
        return SingleAuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .deceasedDate(author.getDeceasedDate())
                .education(author.getEducation())
                .title(author.getTitle())
                .summary(author.getSummary())
                .build();
    }

    public SingleAuthorDTO saveAuthor(UpsertAuthorDTO dto){
        Author author = new Author(dto.getId(),
                dto.getTitle(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getDeceasedDate(),
                dto.getEducation(),
                dto.getSummary());
        authorRepository.save(author);
        return SingleAuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .deceasedDate(author.getDeceasedDate())
                .education(author.getEducation())
                .title(author.getTitle())
                .summary(author.getSummary())
                .build();
    }

    public UpsertAuthorDTO getUpdate(Long id){
        var entity = authorRepository.findById(id).get();
        return new UpsertAuthorDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getDeceasedDate(),
                entity.getEducation(),
                entity.getSummary());
    }

    public void delete(Long id){
        authorRepository.deleteById(id);
    }

    public Page<BookAuthorDTO> getBookAuthorGrid(Integer pageNumber, Long id){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return authorRepository.findBookAuthor(id, pageable);
    }

    public Long getTotalDependencies(Long id){
        return authorRepository.countById(id);
    }



}
