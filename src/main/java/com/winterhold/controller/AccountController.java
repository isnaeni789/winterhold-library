package com.winterhold.controller;

import com.winterhold.dto.account.RegisterDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/login-form")
    public String loginForm(Model model){
        return "account/login-form";
    }

    /*PostMapping untuk login, melalui spring security*/

    @GetMapping("/register-form")
    public String registerForm(Model model){
        RegisterDTO dto = new RegisterDTO();
        model.addAttribute("dto", dto);
        return "account/register-form";
    }

    @PostMapping("/save-account")
    public String register(@Valid @ModelAttribute("dto") RegisterDTO dto,
                           BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "account/register-form";
        }
        service.registerAccount(dto);
        return "redirect:/account/login-form";
    }

    @RequestMapping(value = "/access-denied", method = {RequestMethod.GET, RequestMethod.POST})
    public String accessDenied(Model model){
        return "account/access-denied";
    }
}
