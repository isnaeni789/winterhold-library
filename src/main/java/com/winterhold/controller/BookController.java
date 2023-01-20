package com.winterhold.controller;

import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/upsert-form-book")
    public String upsertForm(@RequestParam(required = false) String code,
                             @RequestParam(required = false) String category,
                             Model model){
        try {
            if (code != null){
                UpdateBookDTO dto = service.getUpdate(code);
                model.addAttribute("actionType", "Update");
                model.addAttribute("dto", dto);
                model.addAttribute("category", dto.getCategoryName());
            } else {
                InsertBookDTO dto = new InsertBookDTO();
                model.addAttribute("actionType", "Insert");
                model.addAttribute("dto", dto);
                model.addAttribute("category", category);
            }
            List<DropdownDTO> authorDropdown = service.getAuthorDropdown();
            model.addAttribute("authorDropdown", authorDropdown);
            return "book/upsert-book-category";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/insert")
    public String save(@Valid @ModelAttribute("dto") InsertBookDTO dto,
                       BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes){
        try {
            if (bindingResult.hasErrors()){
                var actionType = "Insert";
                model.addAttribute("actionType", actionType);
                model.addAttribute("category", dto.getCategoryName());
                List<DropdownDTO> authorDropdown = service.getAuthorDropdown();
                model.addAttribute("authorDropdown", authorDropdown);
                return "book/upsert-book-category";
            }
            service.insertBook(dto);
            redirectAttributes.addAttribute("category", dto.getCategoryName());
            return "redirect:/book/category/detail";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/update")
    public String save(@Valid @ModelAttribute("dto") UpdateBookDTO dto,
                       BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes){
        try {
            if (bindingResult.hasErrors()){
                var actionType = "Update";
                model.addAttribute("actionType", actionType);
                model.addAttribute("category", dto.getCategoryName());
                List<DropdownDTO> authorDropdown = service.getAuthorDropdown();
                model.addAttribute("authorDropdown", authorDropdown);
                return "book/upsert-book-category";
            }
            service.updateBook(dto);
            redirectAttributes.addAttribute("category", dto.getCategoryName());
            return "redirect:/book/category/detail";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String code, Model model,
                         RedirectAttributes redirectAttributes){
        try {
            service.delete(code);
            return "redirect:/book";
        } catch (Exception exception){
            var totalDependencies = service.getTotalDependencies(code);
            var errorMessage =
                    String.format("""
                                    Keterangan: Terdapat %s transaksi pada buku ini.
                                    Maka tidak bisa langsung menghapus buku ini.
                                    Jenis Exception: %s""",
                            totalDependencies, exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }


}
