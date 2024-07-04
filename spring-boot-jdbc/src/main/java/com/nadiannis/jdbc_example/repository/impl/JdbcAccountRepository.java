package com.nadiannis.jdbc_example.repository.impl;

import com.nadiannis.jdbc_example.model.Account;
import com.nadiannis.jdbc_example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAccountRepository implements AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Account account) {
        System.out.println("account: " + account + " : " + account.toString());
        return jdbcTemplate.update(
                "INSERT INTO accounts (account_number, balance) VALUES (?, ?)",
                new Object[]{ account.getAccountNumber(), account.getBalance() }
        );
    }

    @Override
    public int update(Account account) {
        return jdbcTemplate.update(
                "UPDATE accounts SET account_number = ?, balance = ? WHERE id = ?",
                new Object[]{ account.getAccountNumber(), account.getBalance(), account.getId() }
        );
    }

    @Override
    public Account findById(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM accounts WHERE id = ?",
                BeanPropertyRowMapper.newInstance(Account.class),
                id
        );
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM accounts",
                BeanPropertyRowMapper.newInstance(Account.class)
        );
    }

    @Override
    public boolean existsById(Integer id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM accounts WHERE id = ?",
                Integer.class,
                id
        );

        if (count > 0) {
            return true;
        }

        return false;
    }

    @Override
    public int deleteById(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM accounts WHERE id = ?",
                id
        );
    }

    @Override
    public int deposit(float amount, Integer id) {
        return jdbcTemplate.update(
                "UPDATE accounts SET balance = balance + ? WHERE id = ?",
                new Object[]{ amount, id }
        );
    }

    @Override
    public int withdraw(float amount, Integer id) {
        return jdbcTemplate.update(
                "UPDATE accounts SET balance = balance - ? WHERE id = ?",
                new Object[]{ amount, id }
        );
    }

}
