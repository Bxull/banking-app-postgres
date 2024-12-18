package kz.duisembek.bank.service;
import kz.duisembek.bank.model.Transaction;
import kz.duisembek.bank.model.Account;
public interface TransactionService {
    Transaction deposit(Long accountId, Double amount);
    Transaction withdraw(Long accountId, Double amount);
    Transaction transfer(Long fromAccountId, Long toAccountId, Double amount);
}
