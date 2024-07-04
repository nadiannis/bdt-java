package com.nadiannis.spring_rest_api.service;

import com.nadiannis.spring_rest_api.model.Account;

import java.util.List;

public interface AccountService {

    List<Account> listAll();
    Account save(Account account);
    Account get(Integer id);
    void delete(Integer id);
    Account deposit(float amount, Integer id);
    Account withdraw(float amount, Integer id);

}
