package com.nadiannis.jdbc_example.repository.impl;

import com.nadiannis.jdbc_example.model.AccountInfo;
import com.nadiannis.jdbc_example.repository.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAccountInfoRepository implements AccountInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(AccountInfo accountInfo) {
        return jdbcTemplate.update(
                "INSERT INTO account_info (account_id, account_name) VALUES (?, ?)",
                new Object[]{ accountInfo.getAccountId(), accountInfo.getAccountName() }
        );
    }

}
