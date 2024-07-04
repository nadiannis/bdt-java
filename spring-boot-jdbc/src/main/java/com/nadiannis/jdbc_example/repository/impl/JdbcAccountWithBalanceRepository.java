package com.nadiannis.jdbc_example.repository.impl;

import com.nadiannis.jdbc_example.model.Account;
import com.nadiannis.jdbc_example.model.AccountWithBalance;
import com.nadiannis.jdbc_example.repository.AccountWithBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAccountWithBalanceRepository implements AccountWithBalanceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AccountWithBalance> findAll() {
        return jdbcTemplate.query(
                "SELECT account_number, account_name, balance FROM accounts a JOIN account_info ai ON a.id = ai.account_id",
                BeanPropertyRowMapper.newInstance(AccountWithBalance.class)
        );
    }
}
