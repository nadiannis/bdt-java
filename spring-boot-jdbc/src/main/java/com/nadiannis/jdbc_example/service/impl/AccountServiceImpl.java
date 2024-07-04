package com.nadiannis.jdbc_example.service.impl;

import com.nadiannis.jdbc_example.model.Account;
import com.nadiannis.jdbc_example.repository.AccountRepository;
import com.nadiannis.jdbc_example.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository repo;

    @Override
    public List<Account> listAll() {
        return repo.findAll();
    }

    @Override
    public Account get(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Account save(Account account) {
        int updated = repo.save(account);
        return account;
//        if (updated > 0) {
//            return repo.findById(account.getId());
//        } else {
//            return null;
//        }
    }

    @Override
    public Account deposit(float amount, Integer id) {
        int updated = repo.deposit(amount, id);
        if (updated > 0) {
            return repo.findById(id);
        } else {
            return null;
        }
    }

    @Override
    public Account withdraw(float amount, Integer id) {
        int updated = repo.withdraw(amount, id);
        if (updated > 0) {
            return repo.findById(id);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
