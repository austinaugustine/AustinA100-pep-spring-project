package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
     private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> register(Account acc) {
        Optional<Account> existingAccount = accountRepository.findByUsername(acc.getUsername());
        if (existingAccount.isPresent()) {
            return Optional.empty();
        }
        Account savedAccount = accountRepository.save(acc);
        return Optional.of(savedAccount);
    }

    public Optional<Account> login(Account acc) {
        return accountRepository.findByUsername(acc.getUsername())
                .filter(existingAcc -> existingAcc.getPassword().equals(acc.getPassword()));
    }
}