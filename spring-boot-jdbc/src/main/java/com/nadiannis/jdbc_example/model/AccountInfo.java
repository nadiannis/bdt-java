package com.nadiannis.jdbc_example.model;

import lombok.Data;

@Data
public class AccountInfo {

    private Integer id;
    private Integer accountId;
    private String accountName;

    public AccountInfo(Integer accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
    }

}
