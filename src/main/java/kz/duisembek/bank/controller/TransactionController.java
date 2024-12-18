package kz.duisembek.bank.controller;

import kz.duisembek.bank.dto.WithdrawRequest;
import kz.duisembek.bank.model.Transaction;
import kz.duisembek.bank.dto.DepositRequest;
import kz.duisembek.bank.dto.TransferRequest;
import kz.duisembek.bank.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody DepositRequest request) {
        // Валидация входящих данных
        if (request.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Обработка депозита
        Transaction transaction = transactionService.deposit(request.getAccountId(), request.getAmount());

        // Возврат успешного ответа
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody WithdrawRequest request) {
        // Валидация входящих данных
        if (request.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Обработка снятия
        Transaction transaction = transactionService.withdraw(request.getAccountId(), request.getAmount());

        // Проверка на успешность транзакции
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequest request) {
        // Валидация входящих данных
        if (request.getAmount() <= 0 || request.getTargetAccountId() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Обработка перевода
        Transaction transaction = transactionService.transfer(request.getAccountId(), request.getTargetAccountId(), request.getAmount());

        // Проверка на успешность транзакции
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(transaction);
    }
}
