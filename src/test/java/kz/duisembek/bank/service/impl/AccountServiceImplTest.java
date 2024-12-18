package kz.duisembek.bank.service.impl;

import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        account1 = new Account();
        account1.setId(1L);
        account1.setBalance(1000.0);

        account2 = new Account();
        account2.setId(2L);
        account2.setBalance(2000.0);
    }

    @Test
    void testShowAllAccounts_ShouldReturnListOfAccounts() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<Account> accounts = accountService.showAllAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testGetAccountById_ShouldReturnAccount_WhenAccountExists() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));

        Account result = accountService.getAccountById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000.0, result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAccountById_ShouldReturnNull_WhenAccountDoesNotExist() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account result = accountService.getAccountById(1L);

        assertNull(result);
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAccount_ShouldSaveAndReturnAccount() {
        when(accountRepository.save(account1)).thenReturn(account1);

        Account result = accountService.saveAccount(account1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000.0, result.getBalance());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    void testUpdateAccount_ShouldUpdateAndReturnAccount_WhenAccountExists() {
        when(accountRepository.existsById(account1.getId())).thenReturn(true);
        when(accountRepository.save(account1)).thenReturn(account1);

        Account result = accountService.updateAccount(account1);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1000.0, result.getBalance());
        verify(accountRepository, times(1)).existsById(account1.getId());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    void testUpdateAccount_ShouldReturnNull_WhenAccountDoesNotExist() {
        when(accountRepository.existsById(account1.getId())).thenReturn(false);

        Account result = accountService.updateAccount(account1);

        assertNull(result);
        verify(accountRepository, times(1)).existsById(account1.getId());
        verify(accountRepository, never()).save(account1);
    }

    @Test
    void testDeleteAccount_ShouldDeleteAccount_WhenCalled() {
        accountService.deleteAccount(1L);

        verify(accountRepository, times(1)).deleteById(1L);
    }
}
