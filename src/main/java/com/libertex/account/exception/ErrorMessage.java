package com.libertex.account.exception;

import lombok.Data;
import java.util.Date;


@Data
public class ErrorMessage {
    private String className;
    private Date date;
    private String errorDescription;

    public ErrorMessage(Exception exception) {
        this.className = exception.getClass().getSimpleName();
        this.errorDescription = exception.getMessage();
        this.date = new Date();

    }
}
