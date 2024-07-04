package com.nadiannis.jdbc_example.service.impl;

import com.nadiannis.jdbc_example.model.AccountWithBalance;
import com.nadiannis.jdbc_example.repository.AccountWithBalanceRepository;
import com.nadiannis.jdbc_example.service.AccountWithBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountWithBalanceServiceImpl implements AccountWithBalanceService {

    @Autowired
    private AccountWithBalanceRepository repo;

    @Override
    public List<AccountWithBalance> listAll() {
        return repo.findAll();
    }

}
