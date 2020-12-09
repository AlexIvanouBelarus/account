package com.libertex.account.controller;


import com.libertex.account.exception.AccountDoesNotExists;
import com.libertex.account.exception.AccountWasChangedException;
import com.libertex.account.exception.ErrorMessage;
import com.libertex.account.exception.NotEnoughMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({NotEnoughMoneyException.class, AccountWasChangedException.class, AccountDoesNotExists.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return new ResponseEntity(new ErrorMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
