package com.winterhold.controller;

import com.winterhold.dto.author.SingleAuthorDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try {
            var grid = service.getAuthorGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("name", name);
            return "author/author-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id, Model model){
        try {
            UpsertAuthorDTO dto = new UpsertAuthorDTO();
            model.addAttribute("actionType", "Insert New");
            if (id != null){
                dto = service.getUpdate(id);
                model.addAttribute("actionType", "Update");
            }
            model.addAttribute("dto", dto);
            return "author/upsert-author";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertAuthorDTO dto,
                       BindingResult bindingResult, Model model){
        try {
            if (bindingResult.hasErrors()){
                var actionType = dto.getId() != null ? "Update" : "Insert New";
                model.addAttribute("actionType", actionType);
                return "author/upsert-author";
            }
            service.saveAuthor(dto);
            return "redirect:/author";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model,
                         RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            return "redirect:/author/index";
        } catch (Exception exception){
            var totalDependencies = service.getTotalDependencies(id);
            var errorMessage =
                    String.format("""
                                    Keterangan: Terdapat %s Buku yang memiliki author ini.
                                    Maka tidak bisa langsung menghapus author ini.
                                    Jenis Exception: %s""",
                            totalDependencies, exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }

    @GetMapping("/detail")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(required = true) Long id,
                        Model model){
        try {
            var grid = service.getBookAuthorGrid(page, id);
            SingleAuthorDTO oneAuthor = service.getOneAuthor(id);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("id", id);
            model.addAttribute("author", oneAuthor);
            return "author/detail-book";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }
}
