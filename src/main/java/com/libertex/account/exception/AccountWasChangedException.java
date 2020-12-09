package com.libertex.account.exception;

public class AccountWasChangedException extends RuntimeException{
    public AccountWasChangedException(String message) {
        super(message);
    }
}
