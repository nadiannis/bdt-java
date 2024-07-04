package com.nadiannis.spring_rest_api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nadiannis.spring_rest_api.TestDataUtil;
import com.nadiannis.spring_rest_api.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testFindAll() {
        Account account1 = TestDataUtil.createTestAccountA();
        Account account2 = TestDataUtil.createTestAccountB();
        Account account3 = TestDataUtil.createTestAccountC();

        accountRepository.saveAll(List.of(account1, account2, account3));

        List<Account> result = accountRepository.findAll();
        assertEquals(3, result.size());

        System.out.println("expected (size): " + 3);
        System.out.println("result (size): " + result.size());
        System.out.println();
    }

    @Test
    public void testSave() {
        Account account = TestDataUtil.createTestAccountA();
        accountRepository.save(account);

        Account result = accountRepository.findById(account.getId()).orElseThrow();
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(account.getBalance(), result.getBalance());

        System.out.println("expected (id): " + account.getId());
        System.out.println("result (id): " + result.getId());
        System.out.println("expected (accountNumber): " + account.getAccountNumber());
        System.out.println("result (accountNumber): " + result.getAccountNumber());
        System.out.println("expected (balance): " + account.getBalance());
        System.out.println("result (balance): " + result.getBalance());
        System.out.println();
    }

    @Test
    public void testFindOne() {
        Account account = TestDataUtil.createTestAccountA();
        accountRepository.save(account);

        Account result = accountRepository.findById(account.getId()).orElseThrow();
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(account.getBalance(), result.getBalance());

        System.out.println("expected (id): " + account.getId());
        System.out.println("result (id): " + result.getId());
        System.out.println("expected (accountNumber): " + account.getAccountNumber());
        System.out.println("result (accountNumber): " + result.getAccountNumber());
        System.out.println("expected (balance): " + account.getBalance());
        System.out.println("result (balance): " + result.getBalance());
        System.out.println();
    }

    @Test
    public void testUpdate() {
        Account account = TestDataUtil.createTestAccountA();
        accountRepository.save(account);

        account.setBalance(800000);
        Account updatedAccount = accountRepository.save(account);

        Optional<Account> result = accountRepository.findById(updatedAccount.getId());
        assertTrue(result.isPresent());
        assertEquals(result.get().getAccountNumber(), account.getAccountNumber());
        assertEquals(result.get().getBalance(), account.getBalance());

        System.out.println("expected (isPresent): " + true);
        System.out.println("result (isPresent): " + result.isPresent());
        System.out.println("expected (accountNumber): " + account.getAccountNumber());
        System.out.println("result (accountNumber): " + result.get().getAccountNumber());
        System.out.println("expected (balance): " + account.getBalance());
        System.out.println("result (balance): " + result.get().getBalance());
        System.out.println();
    }

    @Test
    public void testDelete() {
        Account account = TestDataUtil.createTestAccountA();
        accountRepository.save(account);

        accountRepository.deleteById(account.getId());

        Optional<Account> result = accountRepository.findById(account.getId());
        assertTrue(result.isEmpty());

        System.out.println("expected (isEmpty): " + true);
        System.out.println("result (isEmpty): " + result.isEmpty());
        System.out.println();
    }

    @Test
    public void testCreateReadDelete() {
        Account account = TestDataUtil.createTestAccountA();
        Account savedAccount = accountRepository.save(account);

        assertNotNull(savedAccount);
        assertNotNull(savedAccount.getId());
        assertThat(savedAccount).usingRecursiveComparison().ignoringFields("id").isEqualTo(account);

        System.out.println("expected (savedAccount): " + "not null");
        System.out.println("result (savedAccount): " + savedAccount);
        System.out.println("expected (savedAccountId): " + "not null");
        System.out.println("result (savedAccountId): " + savedAccount.getId());
        System.out.println("account: " + account);
        System.out.println();

        Iterable<Account> accounts = accountRepository.findAll();
        assertThat(accounts).extracting(Account::getAccountNumber).containsOnlyOnce(account.getAccountNumber());

        System.out.println("expected (one account saved): " + account);
        System.out.println("result (one account saved): " + accounts);
        System.out.println();

        accountRepository.deleteAll();
        assertThat(accountRepository.findAll().isEmpty());

        System.out.println("expected (isEmpty): " + true);
        System.out.println("result (isEmpty): " + accountRepository.findAll().isEmpty());
        System.out.println();
    }

}
