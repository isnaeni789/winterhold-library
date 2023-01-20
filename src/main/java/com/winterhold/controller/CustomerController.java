package com.winterhold.controller;

import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String number,
                        @RequestParam(defaultValue = "") String name,
                        Model model) {
        try {
            var grid = service.getCustomerGrid(page, number, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("name", name);
            model.addAttribute("number", number);
            return "customer/customer-index";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) String number, Model model) {
        try {
            if (number != null) {
                UpdateCustomerDTO dto = service.getUpdate(number);
                model.addAttribute("actionType", "Update");
                model.addAttribute("dto", dto);
            } else {
                InsertCustomerDTO dto = new InsertCustomerDTO();
                model.addAttribute("actionType", "Insert");
                model.addAttribute("dto", dto);
            }
            return "customer/upsert-customer";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @PostMapping("/insert")
    public String save(@Valid @ModelAttribute("dto") InsertCustomerDTO dto,
                       BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                var actionType = "Insert";
                model.addAttribute("actionType", actionType);
                return "customer/upsert-customer";
            }
            service.insertCustomer(dto);
            return "redirect:/customer";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @PostMapping("/update")
    public String save(@Valid @ModelAttribute("dto") UpdateCustomerDTO dto,
                       BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                var actionType = "Update";
                model.addAttribute("actionType", actionType);
                return "customer/upsert-customer";
            }
            service.updateCustomer(dto);
            return "redirect:/customer";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String number, Model model,
                         RedirectAttributes redirectAttributes) {
        try {
            service.delete(number);
            return "redirect:/customer";
        } catch (Exception exception) {
            var totalDependencies = service.getTotalDependencies(number);
            var errorMessage =
                    String.format("""
                                    Keterangan: Terdapat %s transaksi pada customer ini.
                                    Maka tidak bisa langsung menghapus customer ini.
                                    Jenis Exception: %s""",
                            totalDependencies, exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }

    @GetMapping("/info")
    public String info(@RequestParam(required = true) String number, Model model) {
        try {
            SingleCustomerDTO customer = service.getOneCustomer(number);
            model.addAttribute("customer", customer);
            return "customer/customer-info";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }

    @GetMapping("/extend")
    public String extend(@RequestParam(required = true) String number, Model model) {
        try {
            service.extend(number);
            return "redirect:/customer";
        } catch (Exception exception) {
            return "redirect:/error/server";
        }
    }
}
