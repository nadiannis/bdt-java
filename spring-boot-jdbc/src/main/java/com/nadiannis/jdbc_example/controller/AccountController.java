package com.nadiannis.jdbc_example.controller;

import com.nadiannis.jdbc_example.model.Account;
import com.nadiannis.jdbc_example.model.Amount;
import com.nadiannis.jdbc_example.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService service;

    @GetMapping
    public HttpEntity<List<Account>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<Account> getOne(@PathVariable("id") Integer id) {
        Account account = service.get(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<Account> add(@RequestBody Account account) {
        Account savedAccount = service.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public HttpEntity<Account> replace(@PathVariable Integer id, @RequestBody Account account) {
        account.setId(id);
        Account updatedAccount = service.save(account);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposit")
    public HttpEntity<Account> deposit(@PathVariable Integer id, @RequestBody Amount amount) {
        Account updatedAccount = service.deposit(amount.getAmount(), id);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/withdrawal")
    public HttpEntity<Account> withdraw(@PathVariable Integer id, @RequestBody Amount amount) {
        Account updatedAccount = service.withdraw(amount.getAmount(), id);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
