package com.winterhold.controller;

import com.winterhold.dto.category.BookCategoryDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/book/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try {
            var grid = service.getCategoryGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("name", name);
            return "category/category-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) String name, Model model){
        try {
            if (name != null){
                UpdateCategoryDTO dto = service.getUpdate(name);
                model.addAttribute("actionType", "Update");
                model.addAttribute("dto", dto);
            } else {
                InsertCategoryDTO dto = new InsertCategoryDTO();
                model.addAttribute("actionType", "Insert");
                model.addAttribute("dto", dto);
            }
            return "category/upsert-category";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/insert")
    public String save(@Valid @ModelAttribute("dto") InsertCategoryDTO dto,
                       BindingResult bindingResult, Model model){
        try {
            if (bindingResult.hasErrors()){
                var actionType = "Insert";
                model.addAttribute("actionType", actionType);
                return "category/upsert-category";
            }
            service.insertCategory(dto);
            return "redirect:/book";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/update")
    public String save(@Valid @ModelAttribute("dto") UpdateCategoryDTO dto,
                       BindingResult bindingResult, Model model){
        try {
            if (bindingResult.hasErrors()){
                var actionType = "Update";
                model.addAttribute("actionType", actionType);
                return "category/upsert-category";
            }
            service.updateCategory(dto);
            return "redirect:/book";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String name, Model model,
                         RedirectAttributes redirectAttributes){
        try {
            service.delete(name);
            return "redirect:/book";
        } catch (Exception exception){
            var totalDependencies = service.getTotalDependencies(name);
            var errorMessage =
                    String.format("""
                                    Keterangan: Terdapat %s buku yang memiliki kategori ini.
                                    Maka tidak bisa langsung menghapus kategori ini.
                                    Jenis Exception: %s""",
                            totalDependencies, exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }

    @GetMapping("/detail")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String title,
                        @RequestParam(defaultValue = "") String author,
                        @RequestParam(required = true) String category,
                        @RequestParam(required = false) Boolean status,
                        Model model){
        try {
            if (status == null){
                var grid = service.getBookCategoryGrid(page, title, author, category);
                var totalHalaman = grid.getTotalPages();
                model.addAttribute("dataGrid", grid);
                model.addAttribute("totalPages", totalHalaman);
            }
            else {
                var grid = service.getBookCategoryGrid(page, title, author, category, status);
                var totalHalaman = grid.getTotalPages();
                model.addAttribute("status", status);
                model.addAttribute("dataGrid", grid);
                model.addAttribute("totalPages", totalHalaman);
            }
            model.addAttribute("currentPage", page);
            model.addAttribute("title", title);
            model.addAttribute("author", author);
            model.addAttribute("category", service.getOneCategory(category).getName());
            return "category/detail-book";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }


}
