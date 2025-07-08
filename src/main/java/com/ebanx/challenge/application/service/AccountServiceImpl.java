package com.ebanx.challenge.application.service;

import com.ebanx.challenge.domain.model.Account;
import com.ebanx.challenge.domain.port.in.AccountService;
import com.ebanx.challenge.domain.port.out.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account deposit(String accountId, int amount) {
        Account account = createOrDeposit(accountRepository.findById(accountId), accountId, amount);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Optional<Account> withdraw(String accountId, int amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null || account.getBalance() < amount) {
            return Optional.empty();
        }
        account.withdraw(amount);
        accountRepository.save(account);
        return Optional.of(account);
    }

    @Override
    public Optional<Account> findAccount(String accountId) {
        return Optional.ofNullable(accountRepository.findById(accountId));
    }

    public Optional<Account> transfer(String originId, String destinationId, int amount) {
        Optional<Account> originOpt = withdraw(originId, amount);
        if (!originOpt.isPresent()) {
            return Optional.empty();
        }
        Account destination = createOrDeposit(accountRepository.findById(destinationId), destinationId, amount);
        accountRepository.save(destination);
        return Optional.of(destination);
    }

    private Account createOrDeposit(Account account, String accountId, int amount) {
        if (account == null) {
            return new Account(accountId, amount);
        } else {
            account.deposit(amount);
            return account;
        }
    }
}