package kz.duisembek.bank.service;

import kz.duisembek.bank.model.Account;
import java.util.List;

public interface AccountService {
    public List<Account> showAllAccounts();
    public Account getAccountById(Long id);
    public Account  saveAccount(Account account);

    public Account updateAccount(Account account);

    public void deleteAccount(Long id);
}