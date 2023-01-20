package com.winterhold.service;

import com.winterhold.ApplicationUserDetails;
import com.winterhold.dao.AccountRepository;
import com.winterhold.dto.account.RegisterDTO;
import com.winterhold.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAccount(RegisterDTO dto){
        String encode = passwordEncoder.encode(dto.getPassword());
        Account account = new Account(dto.getUsername(), encode);
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username).get();
        ApplicationUserDetails userDetails = new ApplicationUserDetails(account);
        return userDetails;
    }

    public Boolean checkExistingUsername(String username){
        Long countUsername = accountRepository.countUsername(username);
        return countUsername < 1;
    }
}
