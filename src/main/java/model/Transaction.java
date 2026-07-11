package model;

import enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    // Data Fields

    private static Integer nextId = 1;
    private final String id;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String reference;

    // Constructor

    Transaction(TransactionType type, BigDecimal amount, String reference) {
        this.id = "TXN" + nextId++;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.reference = reference;
    }

    // Utility methods

    @Override
    public String toString() {
        return "Transaction" +
                "\n\tid='" +  id + '\'' +
                "\n\ttype=" + type +
                "\n\tamount=" + amount +
                "\n\ttimestamp=" + timestamp +
                "\n\treference='" + reference + '\'' +
                '}';
    }

    // Getters

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getReference() {
        return reference;
    }
}
