package com.ebanx.challenge.adapter.in.web;

import com.ebanx.challenge.domain.model.Account;
import com.ebanx.challenge.domain.port.in.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
                Map<String, Object> depositResponse = new HashMap<>();
                Map<String, Object> destMap = new HashMap<>();
                destMap.put("id", dest.getId());
                destMap.put("balance", dest.getBalance());
                depositResponse.put("destination", destMap);
                return ResponseEntity.status(HttpStatus.CREATED).body(depositResponse);

            case "withdraw":
                Optional<Account> originOpt = accountService.withdraw(eventRequest.getOrigin(), eventRequest.getAmount());
                if (originOpt.isPresent()) {
                    Map<String, Object> withdrawResponse = new HashMap<>();
                    Map<String, Object> originMap = new HashMap<>();
                    originMap.put("id", originOpt.get().getId());
                    originMap.put("balance", originOpt.get().getBalance());
                    withdrawResponse.put("origin", originMap);
                    return ResponseEntity.status(HttpStatus.CREATED).body(withdrawResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
                }

            case "transfer":
                Optional<Account> fromOpt = accountService.withdraw(eventRequest.getOrigin(), eventRequest.getAmount());
                if (fromOpt.isPresent()) {
                    Account to = accountService.deposit(eventRequest.getDestination(), eventRequest.getAmount());
                    Map<String, Object> transferResponse = new HashMap<>();
                    Map<String, Object> originTransfer = new HashMap<>();
                    Map<String, Object> destTransfer = new HashMap<>();
                    originTransfer.put("id", fromOpt.get().getId());
                    originTransfer.put("balance", fromOpt.get().getBalance());
                    destTransfer.put("id", to.getId());
                    destTransfer.put("balance", to.getBalance());
                    transferResponse.put("origin", originTransfer);
                    transferResponse.put("destination", destTransfer);
                    return ResponseEntity.status(HttpStatus.CREATED).body(transferResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
                }

            default:
                return ResponseEntity.badRequest().build();
        }
    }
}