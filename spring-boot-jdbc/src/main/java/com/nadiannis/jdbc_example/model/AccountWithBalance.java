package com.nadiannis.jdbc_example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountWithBalance {

    private String accountNumber;
    private String accountName;
    private float balance;

    public AccountWithBalance(String accountNumber, String accountName, float balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
    }

}
