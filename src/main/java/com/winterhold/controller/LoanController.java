package com.winterhold.controller;

import com.winterhold.dto.book.BookInfoDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import com.winterhold.dto.loan.SingleLoanDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String title,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try {
            var grid = service.getLoanGrid(page, title, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("name", name);
            model.addAttribute("title", title);
            return "loan/loan-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id,
                             Model model){
        try {
            UpsertLoanDTO dto = new UpsertLoanDTO();
            model.addAttribute("actionType", "Insert");
            List<DropdownDTO> bookDropdown = service.getBookDropdown();
            model.addAttribute("bookDropdown", bookDropdown);
            if (id != null){
                dto = service.getUpdate(id);
                model.addAttribute("actionType", "Update");
                bookDropdown = service.getAllBookDropdown();
                model.addAttribute("bookDropdown", bookDropdown);
            }
            List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("dto", dto);
            return "loan/upsert-loan";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("dto") UpsertLoanDTO dto,
                       BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                var actionType = "Insert";
                model.addAttribute("actionType", actionType);
                List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
                model.addAttribute("customerDropdown", customerDropdown);
                List<DropdownDTO> bookDropdown = service.getBookDropdown();
                model.addAttribute("bookDropdown", bookDropdown);
                return "loan/upsert-loan";
            }
            service.insert(dto);
            return "redirect:/loan";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dto") UpsertLoanDTO dto,
                       BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                var actionType = "Update";
                model.addAttribute("actionType", actionType);
                List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
                model.addAttribute("customerDropdown", customerDropdown);
                List<DropdownDTO> bookDropdown = service.getAllBookDropdown();
                model.addAttribute("bookDropdown", bookDropdown);
                return "loan/upsert-loan";
            }
            service.update(dto);
            return "redirect:/loan";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam(required = true) Long id,
                             Model model){
        try {
            LocalDate returnDate = service.getOneLoan(id).getReturnDate();
            if (returnDate == null){
                service.returnBook(id);
            }
            return "redirect:/loan";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/info")
    public String info(@RequestParam(required = true) Long id, Model model) {
        try {
            SingleLoanDTO loan = service.getOneLoan(id);
            model.addAttribute("loan", loan);
            BookInfoDTO book = service.getOneBook(id);
            model.addAttribute("book", book);
            SingleCustomerDTO customer = service.getOneCustomer(id);
            model.addAttribute("customer", customer);
            return "loan/loan-info";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @GetMapping("/upsert-form-modal")
    public String upsertFormModal(@RequestParam(required = false) Long id,
                             Model model){
        try {
            UpsertLoanDTO dto = new UpsertLoanDTO();
            model.addAttribute("actionType", "Insert");
            List<DropdownDTO> bookDropdown = service.getBookDropdown();
            model.addAttribute("bookDropdown", bookDropdown);
            if (id != null){
                dto = service.getUpdate(id);
                model.addAttribute("actionType", "Update");
                bookDropdown = service.getAllBookDropdown();
                model.addAttribute("bookDropdown", bookDropdown);
            }
            List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("dto", dto);
            return "loan/upsert-loan-modal";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/insert-modal")
    public String insertModal(@Valid @ModelAttribute("dto") UpsertLoanDTO dto,
                         BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                var actionType = "Insert";
                model.addAttribute("actionType", actionType);
                List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
                model.addAttribute("customerDropdown", customerDropdown);
                List<DropdownDTO> bookDropdown = service.getBookDropdown();
                model.addAttribute("bookDropdown", bookDropdown);
                return "loan/upsert-loan-modal";
            }
            service.insert(dto);
            return "redirect:/loan";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @PostMapping("/update-modal")
    public String updateModal(@Valid @ModelAttribute("dto") UpsertLoanDTO dto,
                         BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                var actionType = "Update";
                model.addAttribute("actionType", actionType);
                List<DropdownDTO> customerDropdown = service.getCustomerDropdown();
                model.addAttribute("customerDropdown", customerDropdown);
                List<DropdownDTO> bookDropdown = service.getAllBookDropdown();
                model.addAttribute("bookDropdown", bookDropdown);
                return "loan/upsert-loan-modal";
            }
            service.update(dto);
            return "redirect:/loan";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }
}
