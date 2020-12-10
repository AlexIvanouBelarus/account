package com.libertex.account.service;

import com.libertex.account.entity.Account;
import com.libertex.account.exception.AccountDoesNotExists;
import com.libertex.account.exception.AccountWasChangedException;
import com.libertex.account.exception.NotEnoughMoneyException;
import com.libertex.account.repository.AccountRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    public   void testGetAll() {
        Account account = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));
        List<Account> accounts = accountService.getAllAccounts();
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
    }

    @Test
    public   void testGetBYId() {
        Account account = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.of(account));
        Account findAccount = accountService.getAccountById("accountId");
        assertNotNull(findAccount);
        assertEquals("accountId", findAccount.getAccountId());
    }

    @Test
    public   void testGetBYIdNotFound() {
        when(accountRepository.findById("accountIdNotExists")).thenReturn(Optional.empty());
        Assertions.assertThrows(AccountDoesNotExists.class,
                () -> accountService.getAccountById("accountIdNotExists"));
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
    public   void testIncreaseAccountsWhenNewAccount() {
        Account account = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.empty());
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
                .amount(new BigDecimal(11.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.of(accountInDb));
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
        accountService.decreaseAccounts(accountForDec);
        Mockito.verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void decreaseAccountsWhenNotEnoughMoney(){
        Account accountInDb = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        Account accountForDec = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(155.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.of(accountInDb));
        Assertions.assertThrows(NotEnoughMoneyException.class,
                () -> accountService.decreaseAccounts(accountForDec));
    }

    @Test
    public void decreaseAccountsWhenAccountDoesNotExists(){
        Account accountForDec = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(155.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.empty());
        Assertions.assertThrows(AccountDoesNotExists.class,
                () -> accountService.decreaseAccounts(accountForDec));
    }

    @Test
    public void decreaseAccountsWhenAccountWasChanged(){
        Account accountInDb = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(15.5))
                .build();
        Account accountForDec = Account.builder()
                .accountId("accountId")
                .amount(new BigDecimal(1.5))
                .build();
        when(accountRepository.findById("accountId")).thenReturn(Optional.of(accountInDb));
        when(accountRepository.save(any(Account.class))).thenThrow(ObjectOptimisticLockingFailureException.class);
        Assertions.assertThrows(AccountWasChangedException.class,
                () -> accountService.decreaseAccounts(accountForDec));
    }
}