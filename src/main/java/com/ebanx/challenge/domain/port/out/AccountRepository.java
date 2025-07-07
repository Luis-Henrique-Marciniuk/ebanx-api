package com.ebanx.challenge.domain.port.out;

import com.ebanx.challenge.domain.model.Account;

public interface AccountRepository {
    Account findById(String id);
    void save(Account account);
    void clear();
}