package com.nadiannis.spring_rest_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nadiannis.spring_rest_api.model.Account;
import com.nadiannis.spring_rest_api.repository.AccountRepository;
import com.nadiannis.spring_rest_api.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepo;

    @InjectMocks
    AccountService accountService = new AccountServiceImpl();

    @BeforeEach
    void setMockOutput() {
        List<Account> accountList = new ArrayList<Account>();
        accountList.add(new Account("1982094121",10000));
        accountList.add(new Account("1982094122",10000));
        accountList.add(new Account("1982094123",10000));
        accountList.add(new Account("1982094124",10000));
        accountList.add(new Account("1982094125",10000));

        lenient().when(accountRepo.findAll()).thenReturn(accountList);
        lenient().when(accountRepo.save(any())).thenReturn(new Account("1982094121", 1000));
        lenient().when(accountRepo.existsById(anyInt())).thenReturn(Boolean.TRUE);
        lenient().when(accountRepo.findById(anyInt())).thenReturn(Optional.of(new Account("11111",1000)));
    }

    @Test
    void testFindAll() {
        List<Account> accountList = accountService.listAll();

        assertEquals(5, accountList.size());
        verify(accountRepo, times(1)).findAll();
    }

    @Test
    void testCreateAccount() {
        Account account = new Account("1982094125",10000);

        accountService.save(account);
        verify(accountRepo, times(1)).save(account);
    }

    @Test
    void testWithdraw() {
        float amount = 1000;
        Integer id = 1;

        accountService.withdraw(amount, id);
        verify(accountRepo, times(1)).withdraw(amount, id);
    }

    @Test
    void testDeposit() {
        float amount = 1000;
        Integer id = 1;

        accountService.deposit(amount, id);
        verify(accountRepo, times(1)).deposit(amount, id);
    }

    @Test
    void testFindOne() {

        List<Account> accounts = accountService.listAll();
        System.out.println(accounts);

        Integer id = 1;
        Account result = accountService.get(id);

        System.out.println("result: " + result);

        assertEquals("11111", result.getAccountNumber());
        assertEquals(1000, result.getBalance());
        verify(accountRepo, times(1)).findById(id);
    }

    @Test
    void testDelete() {
        Integer id = 1;

        accountService.delete(id);
        verify(accountRepo, times(1)).deleteById(id);
    }

}
