package com.ebanx.challenge.adapter.in.web;

import com.ebanx.challenge.domain.model.Account;
import com.ebanx.challenge.domain.port.in.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam("account_id") String accountId) {
        Optional<Account> account = accountService.findAccount(accountId);
        if (account.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(account.get().getBalance()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
    }

    @PostMapping("/event")
    public ResponseEntity<Object> handleEvent(@RequestBody EventRequest eventRequest) {
        switch (eventRequest.getType()) {
            case "deposit":
                Account dest = accountService.deposit(eventRequest.getDestination(), eventRequest.getAmount());
                return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.fromSingle(dest, false));
            case "withdraw":
                Optional<Account> originOpt = accountService.withdraw(eventRequest.getOrigin(), eventRequest.getAmount());
                if (originOpt.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.fromSingle(originOpt.get(), true));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
                }
            case "transfer":
                Optional<Account> originTransferOpt = accountService.withdraw(eventRequest.getOrigin(), eventRequest.getAmount());
                if (originTransferOpt.isPresent()) {
                    Account destination = accountService.deposit(eventRequest.getDestination(), eventRequest.getAmount());
                    return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.fromTransfer(originTransferOpt.get(), destination));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
                }
            default:
                return ResponseEntity.badRequest().build();
        }
    }
}