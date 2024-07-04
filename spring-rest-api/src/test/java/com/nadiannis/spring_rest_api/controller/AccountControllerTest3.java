package com.nadiannis.spring_rest_api.controller;

import com.nadiannis.spring_rest_api.model.Account;
import com.nadiannis.spring_rest_api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AccountControllerTest3 {
    @LocalServerPort
    private int port;

    @MockBean
    private AccountService accountService;

    private String url;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/", port);

        List<Account> accountList = new ArrayList<Account>();
        accountList.add(new Account("1982094121",10000));
        accountList.add(new Account("1982094122",10000));
        accountList.add(new Account("1982094123",10000));
        accountList.add(new Account("1982094124",10000));
        accountList.add(new Account("1982094125",10000));

        lenient().when(accountService.listAll()).thenReturn(accountList);
    }

    @Test
    public void whenGetRequestToAccounts_thenCorrectResponse() throws Exception {
        log.info("assert GET /api/accounts is correct response");
        ResponseEntity<Account[]> responseEntity = restTemplate.getForEntity("/api/accounts", Account[].class);
        Account[] objects = responseEntity.getBody();
        log.info("objects={}", Arrays.toString(objects));
        Assertions.assertNotNull(objects);
    }

}
