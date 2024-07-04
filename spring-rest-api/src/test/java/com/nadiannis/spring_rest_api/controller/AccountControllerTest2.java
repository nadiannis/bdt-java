package com.nadiannis.spring_rest_api.controller;

import com.nadiannis.spring_rest_api.model.Account;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AccountControllerTest2 {

    @LocalServerPort
    private int port;

    private String url;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/", port);
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
