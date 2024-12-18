package kz.duisembek.bank.model;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDate date;
    private TransactionType  type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id", nullable = true)
    private Account destinationAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType  getType() {
        return type;
    }

    public void setType(TransactionType  type) {
        this.type = type;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account account) {
        this.sourceAccount = account;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account targetAccount) {
        this.destinationAccount = targetAccount;
    }
}
