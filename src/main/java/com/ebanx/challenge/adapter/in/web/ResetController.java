package com.ebanx.challenge.adapter.in.web;

import com.ebanx.challenge.domain.port.out.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {
    private final AccountRepository accountRepository;

    public ResetController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        accountRepository.clear();
        return ResponseEntity.ok().build();
    }
}