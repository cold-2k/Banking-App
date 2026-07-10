# Banking System Design Specification

## Project Overview

This project simulates the core operations of a banking system while applying object-oriented design principles. It will also evolve over time to introduce software engineering concepts such as layered architecture, REST APIs, persistence, authentication, and system design.

**None of these details are exhaustive** and this is a living document, things will evolve.

## Functional Requirements

For a bank, it should be able to **at least**:

1. add customers and create accounts for them
2. deposit and withdraw money
3. transfer funds from one account to another
4. view transaction history

## Business Rules

1. A customer may own one or more accounts.
2. Every account belongs to exactly one customer.
3. Account is created and hence each customer and the account(s) are allocated a unique ID.
4. Every kind of transaction will create a transaction record.
5. Closed accounts cannot perform any transactions.

## Domain Model

```
Bank
│
├── Customer
│      │
│      └── Account
│                │
│                └── Transaction
```

## Relationships

One Bank has many customers.

One Customer owns mulitple accounts.

One Account has multiple Transactions.

One Account belongs to one or more customers.

## Class Design

| **Customer** | id |
| --- | --- |
|  | firstName |
|  | lastName |
|  | email |
|  | phoneNum |
|  | accounts |
| **Bank** | name |
| **Account** | id |
|  | owner |
|  | balance |
|  | status |
|  | dateOfOpening |
|  | transactions |
| **Transaction** | id |
|  | type |
|  | amount |
|  | timestamp |
|  | fromAccountId |
|  | toAccountId |
|  | description |

## Enums

Account type - SAVINGS, CURRENT

Transaction type - DEPOSIT, WITHDRAWAL, TRANSFER

Account status - ACTIVE, INACTIVE, CLOSED