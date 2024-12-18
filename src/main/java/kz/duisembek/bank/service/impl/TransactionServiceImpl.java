package kz.duisembek.bank.service.impl;

import jakarta.transaction.Transactional;
import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.model.Transaction;
import kz.duisembek.bank.model.TransactionType;
import kz.duisembek.bank.repository.AccountRepository;
import kz.duisembek.bank.repository.TransactionRepository;
import kz.duisembek.bank.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class TransactionServiceImpl  implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Transaction deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        Transaction  transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setDestinationAccount(account);
        transaction.setDate(LocalDate.now());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if(account.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setDestinationAccount(account);
        transaction.setDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Account sourceAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Account targetAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (sourceAccount.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient funds");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(sourceAccount.getBalance() + amount);
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setDestinationAccount(targetAccount);
        return transactionRepository.save(transaction);
    }
}
