package com.libertex.account.controller;

import com.libertex.account.entity.Account;
import com.libertex.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan("configuration")
@RequestMapping("accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAll() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/inc")
    public void incAccount(@RequestBody Account account) {
        accountService.increaseAccounts(account);
    }

    @PostMapping("/dec")
    public void decAccount(@RequestBody Account account) {
        accountService.decreaseAccounts(account);
    }
}