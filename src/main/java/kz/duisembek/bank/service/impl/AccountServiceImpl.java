package kz.duisembek.bank.service.impl;

import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.repository.AccountRepository;
import kz.duisembek.bank.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> showAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        if (accountRepository.existsById(account.getId())){
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}
