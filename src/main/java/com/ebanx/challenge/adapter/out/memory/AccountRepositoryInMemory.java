package com.ebanx.challenge.adapter.out.memory;

import com.ebanx.challenge.domain.model.Account;
import com.ebanx.challenge.domain.port.out.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepositoryInMemory implements AccountRepository {
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account findById(String id) {
        return accounts.get(id);
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public void clear() {
        accounts.clear();
    }
}