package com.libertex.account.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("configuration")
public class AccountController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot +++!";
    }

}