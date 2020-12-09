package com.libertex.account.service;

import com.libertex.account.entity.Account;
import com.libertex.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public   void testIncreaseAccounts() {
        Account account = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
        accountService.increaseAccounts(account);
        Mockito.verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void decreaseAccounts() {
        Account accountInDb = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        Account accountForDec = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.of(accountInDb));
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
        accountService.decreaseAccounts(accountForDec);
        Mockito.verify(accountRepository, times(1)).save(any(Account.class));
    }
}