package com.winterhold.service;

import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.category.*;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Page<CategoryGridDTO> getCategoryGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return categoryRepository.findByName(searchName, pageable);
    }


    public SingleCategoryDTO getOneCategory(String name){
        Category category = categoryRepository.findById(name).get();
        return SingleCategoryDTO.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .build();
    }

    public SingleCategoryDTO insertCategory(InsertCategoryDTO dto){
        var category = new Category(dto.getName(), dto.getFloor(), dto.getIsle(), dto.getBay());
        categoryRepository.save(category);
        return SingleCategoryDTO.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .build();
    }

    public SingleCategoryDTO updateCategory(UpdateCategoryDTO dto){
        var category = new Category(dto.getName(), dto.getFloor(), dto.getIsle(), dto.getBay());
        categoryRepository.save(category);
        return SingleCategoryDTO.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .build();
    }

    public UpdateCategoryDTO getUpdate(String name){
        var entity = categoryRepository.findById(name).get();
        return new UpdateCategoryDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay());
    }

    public Boolean isValidName(String name){
        var total = categoryRepository.checkValidName(name);
        return total < 1;
    }

    public void delete(String name){
        categoryRepository.deleteById(name);
    }

    public Page<BookCategoryDTO> getBookCategoryGrid(Integer pageNumber, String title, String author, String category){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return categoryRepository.findBookCategory(title, author, category, pageable);
    }

    public Page<BookCategoryDTO> getBookCategoryGrid(Integer pageNumber,
                                                     String title,
                                                     String author,
                                                     String category,
                                                     Boolean status){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return categoryRepository.findBookCategory(title, author, category, status, pageable);
    }

    public Long getTotalDependencies(String name){
        return categoryRepository.countById(name);
    }

}
