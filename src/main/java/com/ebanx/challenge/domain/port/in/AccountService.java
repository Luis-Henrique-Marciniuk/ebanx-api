package com.ebanx.challenge.domain.port.in;

import com.ebanx.challenge.domain.model.Account;

import java.util.Optional;

public interface AccountService {
    Account deposit(String accountId, int amount);
    Optional<Account> withdraw(String accountId, int amount);
    Optional<Account> findAccount(String accountId);
}