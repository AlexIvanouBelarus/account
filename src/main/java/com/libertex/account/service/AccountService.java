package com.libertex.account.service;

import com.libertex.account.entity.Account;
import com.libertex.account.exception.AccountDoesNotExists;
import com.libertex.account.exception.AccountWasChangedException;
import com.libertex.account.exception.NotEnoughMoneyException;
import com.libertex.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public void increaseAccounts(Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(account.getAccountId());
        Account updatedAccount = optionalAccount.isPresent() ? optionalAccount.get()
                : Account.builder().accountId(account.getAccountId()).amount(BigDecimal.ZERO).build();
        BigDecimal newAmount = updatedAccount.getAmount().add(account.getAmount());
        updatedAccount.setAmount(newAmount);
        accountRepository.save(updatedAccount);
    }

     public void decreaseAccounts(Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(account.getAccountId());
        if (!optionalAccount.isPresent())
            throw new AccountDoesNotExists("Account does not exists");
        Account updatedAccount = optionalAccount.get();
        if (updatedAccount.getAmount().compareTo(account.getAmount()) == -1){
            throw new NotEnoughMoneyException("Not Enough Money");
        }
        BigDecimal newAmount = updatedAccount.getAmount().subtract( account.getAmount());
        updatedAccount.setAmount(newAmount);
        try {
            accountRepository.save(updatedAccount);
        }catch (ObjectOptimisticLockingFailureException e){
            throw new AccountWasChangedException("Account was changed");
        }
    }
}
