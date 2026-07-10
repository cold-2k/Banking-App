package model;

import enums.AccountStatus;
import enums.AccountType;
import enums.TransactionType;

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

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    void deposit(BigDecimal amount) {
        // amount validation
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        // deposit logic
        balance = balance.add(amount);

        // log the transaction
        Transaction newTransaction = new Transaction(TransactionType.CREDIT, amount, accountNumber);
        this.addTransaction(newTransaction);
    }

    void withdraw(BigDecimal amount) {
        // amount validation
        if  (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        // withdraw logic
        balance = balance.subtract(amount);

        // log the transaction
        Transaction newTransaction = new Transaction(TransactionType.DEBIT, amount, accountNumber);
        this.addTransaction(newTransaction);
    }

    void closeAccount() {
        withdraw(balance);
        status = AccountStatus.CLOSED;
    }

    // Utility Methods

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountNumber +
                ", owner=" + owner.getId() +
                ", accountType=" + type +
                ", dateOpened=" + dateOpened +
                ", balance=" + balance +
                ", accountStatus=" + status +
                ", transaction count=" + transactions.size() +
                '}';
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
}

