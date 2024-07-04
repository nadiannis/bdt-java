package com.nadiannis.jdbc_example.repository;

import com.nadiannis.jdbc_example.model.BankInfo;

import java.util.List;

public interface BankInfoRepository {

    public List<BankInfo> findAll();

}
