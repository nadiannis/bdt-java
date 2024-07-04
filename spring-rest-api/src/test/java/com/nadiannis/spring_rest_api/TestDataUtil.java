package com.nadiannis.spring_rest_api;

import com.nadiannis.spring_rest_api.model.Account;

public class TestDataUtil {

    public static Account createTestAccountA() {
        return new Account("12345678", 500000);
    }

    public static Account createTestAccountB() {
        return new Account("87654321", 600000);
    }

    public static Account createTestAccountC() {
        return new Account("13579864", 700000);
    }

}
