package model;

import enums.AccountStatus;
import enums.AccountType;
import enums.TransactionType;
import exception.validation.InvalidAmountException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    // Data Fields

    private static int nextId = 1;
    private final String accountNumber;
    private final Customer owner;
    private final AccountType type;
    private final LocalDate dateOpened;

    private BigDecimal balance;
    private AccountStatus status;

    private final List<Transaction> transactions;

    // Constructor

    Account(Customer owner, AccountType type) {
        this.accountNumber = String.format("ACC%06d", nextId++);

        this.dateOpened = LocalDate.now();
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
        this.transactions = new ArrayList<>();

        this.owner = owner;
        this.type = type;
    }

    // Methods

    // 1. Validations

        // validate the amount <= balance
        private void validateSufficientBalance(BigDecimal amount) {
            if (amount.compareTo(balance) > 0) throw new InvalidAmountException(
                    "Insufficient balance for debit in account with ID: " + accountNumber
            );
        }


    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    void credit(BigDecimal amount) {
        // amount validation
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        // credit logic
        balance = balance.add(amount);

        // log the transaction
        Transaction newTransaction = new Transaction(
                TransactionType.CREDIT,
                amount,
                amount + " credited to account " + accountNumber
        );
        this.addTransaction(newTransaction);
    }

    void debit(BigDecimal amount) {
        // amount validation
        if  (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        // debit logic: balance >= amount
        validateSufficientBalance(amount);
        balance = balance.subtract(amount);

        // log the transaction
        Transaction newTransaction = new Transaction(
                TransactionType.DEBIT,
                amount,
                amount + " debited from account " + accountNumber);
        this.addTransaction(newTransaction);
    }

    // Utility Methods

    @Override
    public String toString() {
        return "Account" +
                "\n\tid=" + accountNumber +
                "\n\towner=" + owner.getId() +
                "\n\taccountType=" + type +
                "\n\tdateOpened=" + dateOpened +
                "\n\tbalance=" + balance +
                "\n\taccountStatus=" + status +
                "\n\ttransaction count=" + transactions.size();
    }

    // Getters and Setters

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    public AccountType getType() {
        return type;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    void setStatus(AccountStatus status) {
        this.status = status;
    }
}

