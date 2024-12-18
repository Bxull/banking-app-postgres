package kz.duisembek.bank.controller;
import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.model.Customer;
import kz.duisembek.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private  final AccountService accountService;

    @GetMapping
    public List<Account> showAllAccounts(){
        return accountService.showAllAccounts();
    }
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }
    @PostMapping("")
    public Account saveAccount(@RequestBody Account account){
        return accountService.saveAccount(account);
    }
    @PutMapping("/{id}")
    public Account updateAccount(@RequestBody Account account){
        return accountService.updateAccount(account);
    }
    @DeleteMapping("/delete_account/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
    }
}
