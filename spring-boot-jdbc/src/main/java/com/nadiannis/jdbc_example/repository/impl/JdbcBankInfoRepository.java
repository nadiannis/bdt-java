package com.nadiannis.jdbc_example.repository.impl;

import com.nadiannis.jdbc_example.model.BankInfo;
import com.nadiannis.jdbc_example.repository.BankInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcBankInfoRepository implements BankInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BankInfo> findAll() {
        return jdbcTemplate.query(
                "SELECT id, account_id, bank_name FROM bank_info",
                BeanPropertyRowMapper.newInstance(BankInfo.class)
        );
    }

}
