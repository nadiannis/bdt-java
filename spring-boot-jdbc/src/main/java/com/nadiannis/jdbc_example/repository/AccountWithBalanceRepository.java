package com.nadiannis.jdbc_example.repository;

import com.nadiannis.jdbc_example.model.AccountWithBalance;

import java.util.List;

public interface AccountWithBalanceRepository {

    public List<AccountWithBalance> findAll();

}
