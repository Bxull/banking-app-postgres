package kz.duisembek.bank.service.impl;

import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.model.Transaction;
import kz.duisembek.bank.model.TransactionType;
import kz.duisembek.bank.repository.AccountRepository;
import kz.duisembek.bank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account account;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100.0);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setDestinationAccount(account);
        transaction.setDate(LocalDate.now());
    }

    @Test
    void testDeposit_ShouldIncreaseBalanceAndCreateTransaction() {
        // Настройка моков
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Вызов метода
        Transaction result = transactionService.deposit(1L, 100.0);

        // Проверки
        assertNotNull(result, "Transaction should not be null");
        assertEquals(1100.0, account.getBalance(), "Account balance should be updated");
        assertEquals(TransactionType.DEPOSIT, result.getType(), "Transaction type should be DEPOSIT");
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(result);
    }

    @Test
    void testWithdraw_ShouldDecreaseBalanceAndCreateTransaction() {
        // Настройка моков
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Вызов метода
        Transaction result = transactionService.withdraw(1L, 100.0);

        // Проверки
        assertNotNull(result, "Transaction should not be null");
        assertEquals(900.0, account.getBalance(), "Account balance should be updated");
        assertEquals(TransactionType.WITHDRAWAL, result.getType(), "Transaction type should be WITHDRAWAL");
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(result);
    }

    @Test
    void testTransfer_ShouldTransferAmountBetweenAccountsAndCreateTransaction() {
        // Создание второго аккаунта для перевода
        Account targetAccount = new Account();
        targetAccount.setId(2L);
        targetAccount.setBalance(500.0);

        // Настройка моков
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(targetAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Вызов метода
        Transaction result = transactionService.transfer(1L, 2L, 200.0);

        // Проверки
        assertNotNull(result, "Transaction should not be null");
        assertEquals(800.0, account.getBalance(), "Source account balance should be updated");
        assertEquals(700.0, targetAccount.getBalance(), "Target account balance should be updated");
        assertEquals(TransactionType.TRANSFER, result.getType(), "Transaction type should be TRANSFER");
        verify(accountRepository, times(2)).save(any(Account.class)); // Проверяем сохранение обоих аккаунтов
        verify(transactionRepository, times(1)).save(result);
    }

    @Test
    void testDeposit_ShouldThrowException_WhenAccountNotFound() {
        // Настройка моков на несуществующий аккаунт
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка, что выбрасывается исключение
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.deposit(1L, 100.0);
        });
        assertEquals("Account not found", thrown.getMessage(), "Exception message should be 'Account not found'");
    }

    @Test
    void testWithdraw_ShouldThrowException_WhenInsufficientFunds() {
        // Настройка моков на аккаунт с недостаточными средствами
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Проверка, что выбрасывается исключение
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.withdraw(1L, 2000.0);  // Попытка снять больше, чем на счету
        });
        assertEquals("Insufficient funds", thrown.getMessage(), "Exception message should be 'Insufficient funds'");
    }
}
