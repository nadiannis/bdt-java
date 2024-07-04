package com.nadiannis.jdbc_example.controller;

import com.nadiannis.jdbc_example.model.Account;
import com.nadiannis.jdbc_example.model.AccountWithBalance;
import com.nadiannis.jdbc_example.service.AccountService;
import com.nadiannis.jdbc_example.service.AccountWithBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account-with-balance")
public class AccountWithBalanceController {

    @Autowired
    private AccountWithBalanceService service;

    @GetMapping
    public HttpEntity<List<AccountWithBalance>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

}
