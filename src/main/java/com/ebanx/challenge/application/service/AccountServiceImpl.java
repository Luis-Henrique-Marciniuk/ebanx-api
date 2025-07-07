package com.ebanx.challenge.application.service;

import com.ebanx.challenge.domain.model.Account;
import com.ebanx.challenge.domain.port.in.AccountService;
import com.ebanx.challenge.domain.port.out.AccountRepository;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccount(String accountId) {
        return Optional.ofNullable(accountRepository.findById(accountId));
    }
    public Account deposit(String accountId, int amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            account = new Account(accountId, 0);
        }
        account.deposit(amount);
        accountRepository.save(account);
        return account;
    }

    public Optional<Account> withdraw(String accountId, int amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null || account.getBalance() < amount) {
            return Optional.empty();
        }
        account.withdraw(amount);
        accountRepository.save(account);
        return Optional.of(account);
    }
}