package com.nadiannis.spring_rest_api.service.impl;

import com.nadiannis.spring_rest_api.exception.AccountAlreadyExistsException;
import com.nadiannis.spring_rest_api.exception.NoSuchAccountExistsException;
import com.nadiannis.spring_rest_api.model.Account;
import com.nadiannis.spring_rest_api.repository.AccountRepository;
import com.nadiannis.spring_rest_api.service.AccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
    public Account save(Account account) {
        try {
            return repo.save(account);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new AccountAlreadyExistsException("Account already exists!", e);
        }
    }

    @Override
    public Account get(Integer id) {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchAccountExistsException("No such account exists!");
        }
    }

    @Override
    public void delete(Integer id) {
        boolean isExists = repo.existsById(id);
        if (isExists) {
            repo.deleteById(id);
        } else {
            throw new NoSuchAccountExistsException("No such account exists!");
        }
    }

    @Override
    public Account deposit(float amount, Integer id) {
        boolean isExists = repo.existsById(id);
        if (isExists) {
            repo.deposit(amount, id);
            return repo.findById(id).get();
        } else {
            throw new NoSuchAccountExistsException("No such account exists!");
        }
    }

    @Override
    public Account withdraw(float amount, Integer id) {
        boolean isExists = repo.existsById(id);
        if (isExists) {
            repo.withdraw(amount, id);
            return repo.findById(id).get();
        } else {
            throw new NoSuchAccountExistsException("No such account exists!");
        }
    }

}
