package com.nadiannis.jdbc_example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankInfo {

    private Integer id;
    private Integer accountId;
    private String bankName;

    public BankInfo(Integer accountId, String bankName) {
        this.accountId = accountId;
        this.bankName = bankName;
    }

}
