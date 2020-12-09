package com.libertex.account.exception;

public class AccountDoesNotExists extends RuntimeException{

    public AccountDoesNotExists(String message) {
        super(message);
    }
}
